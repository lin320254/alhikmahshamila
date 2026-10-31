const {
  initializeTestEnvironment,
  assertFails,
  assertSucceeds,
} = require("@firebase/rules-unit-testing");
const { test, before, after, beforeEach } = require("node:test");
const fs = require("node:fs");

let testEnv;
const PROJECT_ID = process.env.GCP_PROJECT || "demo-no-project";
const ALICE_UID = "alice_123";
const BOB_UID = "bob_456";

const [emulatorHost, emulatorPortStr] = (process.env.FIRESTORE_EMULATOR_HOST || "127.0.0.1:8085").split(":");
const emulatorPort = parseInt(emulatorPortStr, 10);

before(async () => {
  const rules = fs.readFileSync("./firestore.rules", "utf8");
  testEnv = await initializeTestEnvironment({
    projectId: PROJECT_ID,
    firestore: {
      rules,
      host: emulatorHost,
      port: emulatorPort,
    },
  });
});

after(async () => {
  if (testEnv) {
    await testEnv.cleanup();
  }
});

beforeEach(async () => {
  if (testEnv) {
    await testEnv.clearFirestore();
  }
});

test("Unauthenticated user: cannot read user profile or books", async () => {
  const unauthDb = testEnv.unauthenticatedContext().firestore();
  await assertFails(unauthDb.collection("users").doc(ALICE_UID).get());
  await assertFails(unauthDb.collection("users").doc(ALICE_UID).collection("books").get());
});

test("Authenticated user: cannot read another user's books", async () => {
  await testEnv.withSecurityRulesDisabled(async (context) => {
    await context.firestore().collection("users").doc(ALICE_UID).collection("books").doc("book1").set({
      id: "book1",
      userId: ALICE_UID,
      title: "Al-Hidayah",
      category: "Fiqh",
      url: "https://shamela.ws/book/123",
      isFavorite: false,
      createdAt: new Date(),
    });
  });

  const bobDb = testEnv.authenticatedContext(BOB_UID).firestore();
  await assertFails(bobDb.collection("users").doc(ALICE_UID).collection("books").doc("book1").get());
  await assertFails(bobDb.collection("users").doc(ALICE_UID).collection("books").get());
});

test("Authenticated user: can create and read their own book", async () => {
  const aliceDb = testEnv.authenticatedContext(ALICE_UID).firestore();
  await assertSucceeds(
    aliceDb.collection("users").doc(ALICE_UID).collection("books").doc("book1").set({
      id: "book1",
      userId: ALICE_UID,
      title: "Al-Hidayah",
      author: "Al-Marghinani",
      category: "Fiqh Hanafi",
      url: "https://shamela.ws/book/123",
      notes: "Classical reference",
      isFavorite: true,
      createdAt: new Date(),
    })
  );

  await assertSucceeds(
    aliceDb.collection("users").doc(ALICE_UID).collection("books").doc("book1").get()
  );
});

test("Authenticated user: cannot create a book claiming someone else's userId", async () => {
  const aliceDb = testEnv.authenticatedContext(ALICE_UID).firestore();
  await assertFails(
    aliceDb.collection("users").doc(BOB_UID).collection("books").doc("book1").set({
      id: "book1",
      userId: BOB_UID,
      title: "Al-Hidayah",
      category: "Fiqh Hanafi",
      url: "https://shamela.ws/book/123",
      isFavorite: true,
      createdAt: new Date(),
    })
  );
});
