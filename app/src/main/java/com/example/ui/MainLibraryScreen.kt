package com.example.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.automirrored.filled.OpenInNew
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.CollectionsBookmark
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Laptop
import androidx.compose.material.icons.filled.LocalLibrary
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Storage
import androidx.compose.material.icons.filled.ViewAgenda
import androidx.compose.material.icons.filled.ViewCarousel
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.BookEntity
import com.example.data.CategoryEntity
import com.example.ui.theme.AmiriFontFamily
import com.example.ui.theme.JameelNooriFontFamily
import com.example.ui.theme.LuminousGreenText
import com.example.ui.theme.RadiantGreenPrimary
import com.example.ui.theme.TextMutedGreen
import com.example.ui.theme.TextWhitePrimary

/**
 * Main Library Screen displaying books loaded from the local Room database,
 * neatly categorized by genre/discipline with real-time filtering, genre shelves,
 * quick genre jump navigation, and responsive layouts.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainLibraryScreen(
    viewModel: BookViewModel,
    modifier: Modifier = Modifier
) {
    val allBooks by viewModel.allRoomBooks.collectAsState()
    val categories by viewModel.categories.collectAsState()
    val selectedCategory by viewModel.selectedCategory.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    val currentLang by viewModel.currentLanguage.collectAsState()
    val viewMode by viewModel.libraryViewMode.collectAsState()
    val currentAppScreen by viewModel.currentAppScreen.collectAsState()

    val showAddBookDialog by viewModel.showAddBookDialog.collectAsState()
    val bookToEdit by viewModel.bookToEdit.collectAsState()
    val showAddCategoryDialog by viewModel.showAddCategoryDialog.collectAsState()
    val bookToDelete by viewModel.bookToDelete.collectAsState()
    val currentUser by viewModel.currentUser.collectAsState()

    var showAiServicesDialog by remember { mutableStateOf(false) }
    var showLaptopGuideDialog by remember { mutableStateOf(false) }
    var showAccountMenu by remember { mutableStateOf(false) }

    // Filter books by real-time search query if active
    val filteredBooks = remember(allBooks, searchQuery) {
        val trimmed = searchQuery.trim()
        if (trimmed.isEmpty()) {
            allBooks
        } else {
            allBooks.filter { book ->
                ArabicSearchUtils.matches(
                    trimmed,
                    book.title,
                    book.author,
                    book.category,
                    book.notes
                )
            }
        }
    }

    // Group filtered books by category/genre
    val booksByGenre = remember(filteredBooks, categories) {
        filteredBooks.groupBy { it.category }
    }

    // RTL text direction for Urdu and Arabic
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        GlassScaffoldBackground(modifier = modifier) {
            Scaffold(
                modifier = Modifier.fillMaxSize(),
                containerColor = Color.Transparent,
                topBar = {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color(0xD8050D08))
                    ) {
                        TopAppBar(
                            title = {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.padding(vertical = 4.dp)
                                ) {
                                    // Liquid Glass Islamic Library Emblem
                                    Surface(
                                        shape = RoundedCornerShape(14.dp),
                                        color = Color(0x3522C55E),
                                        border = BorderStroke(1.2.dp, RadiantGreenPrimary),
                                        modifier = Modifier.size(42.dp)
                                    ) {
                                        Box(contentAlignment = Alignment.Center) {
                                            Icon(
                                                imageVector = Icons.Default.CollectionsBookmark,
                                                contentDescription = null,
                                                tint = RadiantGreenPrimary,
                                                modifier = Modifier.size(24.dp)
                                            )
                                        }
                                    }

                                    Spacer(modifier = Modifier.width(12.dp))

                                    Column {
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Text(
                                                text = Strings.libraryMainTitle(currentLang),
                                                fontFamily = if (currentLang == AppLanguage.URDU) JameelNooriFontFamily else AmiriFontFamily,
                                                fontWeight = FontWeight.Bold,
                                                fontSize = if (currentLang == AppLanguage.URDU) 22.sp else 20.sp,
                                                color = TextWhitePrimary
                                            )
                                            Spacer(modifier = Modifier.width(8.dp))
                                            // Room DB Tag
                                            Surface(
                                                shape = RoundedCornerShape(8.dp),
                                                color = Color(0x2034D399),
                                                border = BorderStroke(0.8.dp, Color(0x6034D399))
                                            ) {
                                                Text(
                                                    text = "Room DB",
                                                    fontSize = 10.sp,
                                                    fontWeight = FontWeight.Bold,
                                                    color = RadiantGreenPrimary,
                                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                                )
                                            }
                                        }
                                        Text(
                                            text = Strings.librarySubtitle(currentLang),
                                            fontFamily = if (currentLang == AppLanguage.URDU) JameelNooriFontFamily else AmiriFontFamily,
                                            fontSize = if (currentLang == AppLanguage.URDU) 14.sp else 11.sp,
                                            color = LuminousGreenText
                                        )
                                    }
                                }
                            },
                            actions = {
                                // Shelf vs Grid toggle
                                Surface(
                                    shape = RoundedCornerShape(16.dp),
                                    color = Color(0x301E382A),
                                    border = BorderStroke(1.dp, Color(0x4034D399)),
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(16.dp))
                                        .clickable {
                                            val nextMode = if (viewMode == LibraryViewMode.CATEGORIZED_SHELVES) {
                                                LibraryViewMode.EXPANDED_GRID
                                            } else {
                                                LibraryViewMode.CATEGORIZED_SHELVES
                                            }
                                            viewModel.setLibraryViewMode(nextMode)
                                        }
                                        .testTag("toggle_library_view_mode_btn")
                                ) {
                                    Row(
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Icon(
                                            imageVector = if (viewMode == LibraryViewMode.CATEGORIZED_SHELVES) {
                                                Icons.Default.GridView
                                            } else {
                                                Icons.Default.ViewCarousel
                                            },
                                            contentDescription = "Toggle View",
                                            tint = RadiantGreenPrimary,
                                            modifier = Modifier.size(18.dp)
                                        )
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text(
                                            text = if (viewMode == LibraryViewMode.CATEGORIZED_SHELVES) {
                                                Strings.viewModeGrid(currentLang)
                                            } else {
                                                Strings.viewModeShelves(currentLang)
                                            },
                                            fontFamily = if (currentLang == AppLanguage.URDU) JameelNooriFontFamily else AmiriFontFamily,
                                            fontSize = 11.sp,
                                            color = TextWhitePrimary
                                        )
                                    }
                                }

                                Spacer(modifier = Modifier.width(6.dp))

                                // AI Assistant Button [ ✨ AI ]
                                Surface(
                                    shape = RoundedCornerShape(18.dp),
                                    color = Color(0x3522C55E),
                                    border = BorderStroke(1.2.dp, RadiantGreenPrimary),
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(18.dp))
                                        .clickable { showAiServicesDialog = true }
                                        .testTag("ai_library_corner_btn")
                                ) {
                                    Row(
                                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.AutoAwesome,
                                            contentDescription = "AI",
                                            tint = RadiantGreenPrimary,
                                            modifier = Modifier.size(15.dp)
                                        )
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text(
                                            text = "AI",
                                            fontWeight = FontWeight.Black,
                                            fontSize = 13.sp,
                                            color = RadiantGreenPrimary
                                        )
                                    }
                                }

                                Spacer(modifier = Modifier.width(6.dp))

                                // Language Switcher [العربية | اردو]
                                Surface(
                                    shape = RoundedCornerShape(22.dp),
                                    color = Color(0x35142E20),
                                    border = BorderStroke(1.dp, Color(0x604ADE80))
                                ) {
                                    Row(
                                        modifier = Modifier.padding(3.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Surface(
                                            shape = RoundedCornerShape(16.dp),
                                            color = if (currentLang == AppLanguage.ARABIC) RadiantGreenPrimary else Color.Transparent,
                                            modifier = Modifier
                                                .clip(RoundedCornerShape(16.dp))
                                                .clickable { viewModel.setLanguage(AppLanguage.ARABIC) }
                                                .testTag("library_lang_arabic_btn")
                                        ) {
                                            Text(
                                                text = "العربية",
                                                fontFamily = AmiriFontFamily,
                                                fontSize = 12.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = if (currentLang == AppLanguage.ARABIC) Color(0xFF021B0B) else TextMutedGreen,
                                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                            )
                                        }

                                        Surface(
                                            shape = RoundedCornerShape(16.dp),
                                            color = if (currentLang == AppLanguage.URDU) RadiantGreenPrimary else Color.Transparent,
                                            modifier = Modifier
                                                .clip(RoundedCornerShape(16.dp))
                                                .clickable { viewModel.setLanguage(AppLanguage.URDU) }
                                                .testTag("library_lang_urdu_btn")
                                        ) {
                                            Text(
                                                text = "اردو",
                                                fontFamily = JameelNooriFontFamily,
                                                fontSize = 14.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = if (currentLang == AppLanguage.URDU) Color(0xFF021B0B) else TextMutedGreen,
                                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                                            )
                                        }
                                    }
                                }

                                Spacer(modifier = Modifier.width(4.dp))

                                // Account & Guide Menu Icon
                                Box {
                                    IconButton(
                                        onClick = { showAccountMenu = true },
                                        modifier = Modifier.testTag("library_account_menu_btn")
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.AccountCircle,
                                            contentDescription = "Account & Settings",
                                            tint = RadiantGreenPrimary,
                                            modifier = Modifier.size(26.dp)
                                        )
                                    }

                                    DropdownMenu(
                                        expanded = showAccountMenu,
                                        onDismissRequest = { showAccountMenu = false },
                                        modifier = Modifier.background(Color(0xFF07190F))
                                    ) {
                                        DropdownMenuItem(
                                            text = {
                                                Column(modifier = Modifier.padding(vertical = 4.dp)) {
                                                    Text(
                                                        text = currentUser?.displayName ?: "User",
                                                        fontFamily = if (currentLang == AppLanguage.URDU) JameelNooriFontFamily else AmiriFontFamily,
                                                        fontWeight = FontWeight.Bold,
                                                        color = TextWhitePrimary,
                                                        fontSize = 15.sp
                                                    )
                                                    Text(
                                                        text = currentUser?.email ?: "",
                                                        color = TextMutedGreen,
                                                        fontSize = 12.sp
                                                    )
                                                }
                                            },
                                            onClick = {}
                                        )
                                        DropdownMenuItem(
                                            text = {
                                                Row(verticalAlignment = Alignment.CenterVertically) {
                                                    Icon(
                                                        imageVector = Icons.Default.Laptop,
                                                        contentDescription = null,
                                                        tint = RadiantGreenPrimary,
                                                        modifier = Modifier.size(16.dp)
                                                    )
                                                    Spacer(modifier = Modifier.width(8.dp))
                                                    Text(
                                                        text = if (currentLang == AppLanguage.URDU) "لیپ ٹاپ اور ویب گائیڈ" else "استخدام اللابتوب والويب",
                                                        fontFamily = if (currentLang == AppLanguage.URDU) JameelNooriFontFamily else AmiriFontFamily,
                                                        color = TextWhitePrimary,
                                                        fontSize = 13.sp
                                                    )
                                                }
                                            },
                                            onClick = {
                                                showAccountMenu = false
                                                showLaptopGuideDialog = true
                                            }
                                        )
                                        DropdownMenuItem(
                                            text = {
                                                Text(
                                                    text = if (currentLang == AppLanguage.URDU) "لاگ آؤٹ کریں" else "تسجيل الخروج",
                                                    fontFamily = if (currentLang == AppLanguage.URDU) JameelNooriFontFamily else AmiriFontFamily,
                                                    color = Color(0xFFF87171),
                                                    fontWeight = FontWeight.Bold,
                                                    fontSize = 14.sp
                                                )
                                            },
                                            onClick = {
                                                showAccountMenu = false
                                                viewModel.signOut()
                                            }
                                        )
                                    }
                                }
                            },
                            colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
                        )

                        // Pinned Search Bar inside Library
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .widthIn(max = 840.dp)
                                .padding(horizontal = 16.dp, vertical = 4.dp)
                        ) {
                            LibrarySearchBar(
                                query = searchQuery,
                                onQueryChange = { viewModel.setSearchQuery(it) },
                                onClear = { viewModel.clearSearch() },
                                lang = currentLang,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }

                        // Search result summary banner
                        if (searchQuery.isNotBlank()) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp, vertical = 4.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = Strings.searchResultsCount(filteredBooks.size, currentLang),
                                    fontFamily = if (currentLang == AppLanguage.URDU) JameelNooriFontFamily else AmiriFontFamily,
                                    color = RadiantGreenPrimary,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 13.sp
                                )
                                Text(
                                    text = Strings.clearSearch(currentLang),
                                    fontFamily = if (currentLang == AppLanguage.URDU) JameelNooriFontFamily else AmiriFontFamily,
                                    color = Color(0xFFF87171),
                                    fontSize = 12.sp,
                                    modifier = Modifier.clickable { viewModel.clearSearch() }
                                )
                            }
                        }

                        // Subtle bottom divider
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(1.dp)
                                .background(
                                    Brush.horizontalGradient(
                                        listOf(Color.Transparent, Color(0x3522C55E), Color.Transparent)
                                    )
                                )
                        )
                    }
                },
                bottomBar = {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .widthIn(max = 840.dp)
                        ) {
                            LibraryBottomNavigationBar(
                                currentScreen = currentAppScreen,
                                lang = currentLang,
                                onNavigateHome = {
                                    viewModel.selectAppScreen(ActiveAppScreen.HOME)
                                },
                                onNavigateLibrary = {
                                    viewModel.selectAppScreen(ActiveAppScreen.MAIN_LIBRARY)
                                    viewModel.selectCategory(null)
                                },
                                onOpenFavorites = {
                                    viewModel.selectCategory(SPECIAL_CATEGORY_FAVORITES)
                                },
                                onAddNewBook = {
                                    viewModel.showAddBook(selectedCategory)
                                }
                            )
                        }
                    }
                },
                floatingActionButton = {
                    ExtendedFloatingActionButton(
                        onClick = { viewModel.showAddBook(selectedCategory) },
                        icon = {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = null,
                                modifier = Modifier.size(20.dp),
                                tint = Color(0xFF03190C)
                            )
                        },
                        text = {
                            Text(
                                text = Strings.addBook(currentLang),
                                fontFamily = if (currentLang == AppLanguage.URDU) JameelNooriFontFamily else AmiriFontFamily,
                                fontSize = if (currentLang == AppLanguage.URDU) 17.sp else 15.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF03190C)
                            )
                        },
                        containerColor = RadiantGreenPrimary,
                        contentColor = Color(0xFF03190C),
                        shape = RoundedCornerShape(26.dp),
                        modifier = Modifier
                            .border(BorderStroke(1.dp, Color(0x8086EFAC)), RoundedCornerShape(26.dp))
                            .testTag("library_add_book_fab")
                    )
                }
            ) { innerPadding ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    contentAlignment = Alignment.TopCenter
                ) {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .widthIn(max = 1440.dp)
                            .testTag("main_library_list"),
                        contentPadding = PaddingValues(top = 12.dp, bottom = 96.dp)
                    ) {
                        // 1. Library Room Overview Stats Banner
                        item {
                            Box(modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)) {
                                LibraryStatsHeroCard(
                                    totalBooks = allBooks.size,
                                    totalGenres = categories.size,
                                    totalFavorites = allBooks.count { it.isFavorite },
                                    lang = currentLang,
                                    onAddCategory = { viewModel.showAddCategory() }
                                )
                            }
                        }

                        // 2. Genre Quick Jump Filter Strip
                        item {
                            GenreQuickJumpRow(
                                categories = categories,
                                selectedCategory = selectedCategory,
                                booksCountByGenre = allBooks.groupingBy { it.category }.eachCount(),
                                lang = currentLang,
                                onSelectCategory = { cat: String? ->
                                    if (selectedCategory == cat) {
                                        viewModel.selectCategory(null)
                                    } else {
                                        viewModel.selectCategory(cat)
                                    }
                                }
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                        }

                        // 3. Categorized Books by Genre
                        // Determine which genres to show:
                        val visibleCategories = if (selectedCategory != null && selectedCategory != SPECIAL_CATEGORY_FAVORITES) {
                            categories.filter { it.name == selectedCategory }
                        } else if (selectedCategory == SPECIAL_CATEGORY_FAVORITES) {
                            // Show favorites section
                            emptyList()
                        } else {
                            categories
                        }

                        if (selectedCategory == SPECIAL_CATEGORY_FAVORITES) {
                            // Dedicated Favorites Section
                            val favBooks = allBooks.filter { it.isFavorite }
                            item {
                                GenreSectionHeader(
                                    title = "⭐ " + Strings.favorites(currentLang),
                                    description = if (currentLang == AppLanguage.URDU) "آپ کی پسندیدہ محفوظ شدہ کتب" else "الكتب المفضلة والمحفوظة لديك",
                                    icon = Icons.Default.Favorite,
                                    booksCount = favBooks.size,
                                    lang = currentLang,
                                    onAddBook = { viewModel.showAddBook(null) }
                                )
                                Spacer(modifier = Modifier.height(8.dp))

                                if (favBooks.isEmpty()) {
                                    EmptyGenreShelfPlaceholder(
                                        genreName = Strings.favorites(currentLang),
                                        lang = currentLang,
                                        onAddBook = {}
                                    )
                                } else {
                                    BooksHorizontalShelf(
                                        books = favBooks,
                                        lang = currentLang,
                                        onOpenBook = { viewModel.openReader(it) },
                                        onToggleFavorite = { viewModel.toggleFavorite(it) },
                                        onEdit = { viewModel.showEditBook(it) },
                                        onDelete = { viewModel.confirmDeleteBook(it) }
                                    )
                                }
                                Spacer(modifier = Modifier.height(20.dp))
                            }
                        } else {
                            // Render every genre
                            items(visibleCategories, key = { it.id }) { category ->
                                val booksInThisGenre = booksByGenre[category.name] ?: emptyList()

                                // If search is active and this genre has no matches, skip it
                                if (searchQuery.isNotBlank() && booksInThisGenre.isEmpty()) {
                                    // Skip genres with no search matches
                                } else {
                                    GenreSectionBlock(
                                        category = category,
                                        books = booksInThisGenre,
                                        viewMode = viewMode,
                                        lang = currentLang,
                                        onOpenBook = { viewModel.openReader(it) },
                                        onToggleFavorite = { viewModel.toggleFavorite(it) },
                                        onEdit = { viewModel.showEditBook(it) },
                                        onDelete = { viewModel.confirmDeleteBook(it) },
                                        onAddBookToGenre = { viewModel.showAddBook(category.name) }
                                    )
                                    Spacer(modifier = Modifier.height(20.dp))
                                }
                            }
                        }

                        // Also display any books in categories not present in default category list (e.g. custom or imported)
                        val knownCategoryNames = categories.map { it.name }.toSet()
                        val extraCategories = booksByGenre.keys.filter { it !in knownCategoryNames }
                        if (extraCategories.isNotEmpty() && selectedCategory == null) {
                            items(extraCategories) { extraGenre ->
                                val booksInThisGenre = booksByGenre[extraGenre] ?: emptyList()
                                GenreSectionBlock(
                                    category = CategoryEntity(name = extraGenre),
                                    books = booksInThisGenre,
                                    viewMode = viewMode,
                                    lang = currentLang,
                                    onOpenBook = { viewModel.openReader(it) },
                                    onToggleFavorite = { viewModel.toggleFavorite(it) },
                                    onEdit = { viewModel.showEditBook(it) },
                                    onDelete = { viewModel.confirmDeleteBook(it) },
                                    onAddBookToGenre = { viewModel.showAddBook(extraGenre) }
                                )
                                Spacer(modifier = Modifier.height(20.dp))
                            }
                        }
                    }
                }
            }

            // Dialogs
            if (showAddBookDialog) {
                AddEditBookDialog(
                    bookToEdit = bookToEdit,
                    categories = categories,
                    initialCategory = selectedCategory,
                    lang = currentLang,
                    onDismiss = { viewModel.dismissBookDialog() },
                    onSave = { title, author, category, url, notes ->
                        viewModel.saveBook(title, author, category, url, notes)
                    },
                    onAddNewCategoryRequest = {
                        viewModel.dismissBookDialog()
                        viewModel.showAddCategory()
                    }
                )
            }

            if (showAddCategoryDialog) {
                AddCategoryDialog(
                    lang = currentLang,
                    onDismiss = { viewModel.dismissAddCategory() },
                    onSave = { name, nameMm ->
                        viewModel.saveCategory(name, nameMm)
                    }
                )
            }

            bookToDelete?.let { book ->
                DeleteConfirmDialog(
                    book = book,
                    lang = currentLang,
                    onDismiss = { viewModel.dismissDeleteDialog() },
                    onConfirm = { viewModel.deleteConfirmedBook() }
                )
            }

            if (showAiServicesDialog) {
                AiServicesDialog(
                    lang = currentLang,
                    onDismiss = { showAiServicesDialog = false },
                    onOpenServiceInReader = { book ->
                        viewModel.openReader(book)
                    }
                )
            }

            if (showLaptopGuideDialog) {
                LaptopWebGuideDialog(
                    lang = currentLang,
                    onDismiss = { showLaptopGuideDialog = false }
                )
            }
        }
    }
}

/**
 * Overview statistics hero card showing total Room DB metrics
 */
@Composable
private fun LibraryStatsHeroCard(
    totalBooks: Int,
    totalGenres: Int,
    totalFavorites: Int,
    lang: AppLanguage,
    onAddCategory: () -> Unit
) {
    Surface(
        shape = RoundedCornerShape(22.dp),
        color = Color(0x3512281D),
        border = BorderStroke(1.dp, Color(0x5534D399)),
        modifier = Modifier
            .fillMaxWidth()
            .testTag("library_stats_card")
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Storage,
                        contentDescription = null,
                        tint = RadiantGreenPrimary,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = Strings.roomDatabaseBadge(lang),
                        fontFamily = if (lang == AppLanguage.URDU) JameelNooriFontFamily else AmiriFontFamily,
                        fontWeight = FontWeight.Bold,
                        color = RadiantGreenPrimary,
                        fontSize = 13.sp
                    )
                }

                // Add Category Pill Button
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = Color(0x2834D399),
                    border = BorderStroke(1.dp, Color(0x6034D399)),
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .clickable(onClick = onAddCategory)
                        .testTag("library_add_genre_btn")
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = null,
                            tint = RadiantGreenPrimary,
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = Strings.addCategory(lang),
                            fontFamily = if (lang == AppLanguage.URDU) JameelNooriFontFamily else AmiriFontFamily,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextWhitePrimary
                        )
                    }
                }
            }

            // Stat counters row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                StatCounterPill(
                    count = totalBooks.toString(),
                    label = Strings.totalLibraryBooks(lang),
                    icon = Icons.Default.MenuBook,
                    iconTint = RadiantGreenPrimary,
                    lang = lang,
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(8.dp))
                StatCounterPill(
                    count = totalGenres.toString(),
                    label = Strings.totalGenres(lang),
                    icon = Icons.Default.CollectionsBookmark,
                    iconTint = Color(0xFF6EE7B7),
                    lang = lang,
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(8.dp))
                StatCounterPill(
                    count = totalFavorites.toString(),
                    label = Strings.favorites(lang),
                    icon = Icons.Default.Star,
                    iconTint = Color(0xFFFBBF24),
                    lang = lang,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
private fun StatCounterPill(
    count: String,
    label: String,
    icon: ImageVector,
    iconTint: Color,
    lang: AppLanguage,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = RoundedCornerShape(14.dp),
        color = Color(0x20000000),
        border = BorderStroke(0.8.dp, Color(0x3034D399)),
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.padding(vertical = 8.dp, horizontal = 6.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = iconTint,
                modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = count,
                fontWeight = FontWeight.Black,
                fontSize = 18.sp,
                color = TextWhitePrimary
            )
            Text(
                text = label,
                fontFamily = if (lang == AppLanguage.URDU) JameelNooriFontFamily else AmiriFontFamily,
                fontSize = if (lang == AppLanguage.URDU) 12.sp else 10.sp,
                color = TextMutedGreen,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center
            )
        }
    }
}

/**
 * Genre quick-jump horizontal bar
 */
@Composable
private fun GenreQuickJumpRow(
    categories: List<CategoryEntity>,
    selectedCategory: String?,
    booksCountByGenre: Map<String, Int>,
    lang: AppLanguage,
    onSelectCategory: (String?) -> Unit
) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // "All Genres" Chip
        item {
            val isSelected = selectedCategory == null
            GenreChip(
                name = Strings.allGenres(lang),
                count = booksCountByGenre.values.sum(),
                icon = Icons.Default.LocalLibrary,
                isSelected = isSelected,
                lang = lang,
                onClick = { onSelectCategory(null) }
            )
        }

        // Favorites Quick Chip
        item {
            val isSelected = selectedCategory == SPECIAL_CATEGORY_FAVORITES
            GenreChip(
                name = Strings.favorites(lang),
                count = null,
                icon = Icons.Default.Star,
                isSelected = isSelected,
                lang = lang,
                onClick = { onSelectCategory(SPECIAL_CATEGORY_FAVORITES) }
            )
        }

        // Individual Genre Chips from Room Database
        items(categories, key = { it.id }) { cat ->
            val isSelected = selectedCategory == cat.name
            val count = booksCountByGenre[cat.name] ?: 0
            GenreChip(
                name = Strings.localizedCategoryName(cat.name, lang),
                count = count,
                icon = getGenreIcon(cat.name),
                isSelected = isSelected,
                lang = lang,
                onClick = { onSelectCategory(cat.name) }
            )
        }
    }
}

@Composable
private fun GenreChip(
    name: String,
    count: Int?,
    icon: ImageVector,
    isSelected: Boolean,
    lang: AppLanguage,
    onClick: () -> Unit
) {
    Surface(
        shape = RoundedCornerShape(20.dp),
        color = if (isSelected) RadiantGreenPrimary else Color(0x35142E20),
        border = BorderStroke(
            1.dp,
            if (isSelected) Color(0xFF86EFAC) else Color(0x4034D399)
        ),
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .clickable(onClick = onClick)
            .testTag("genre_chip_$name")
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = if (isSelected) Color(0xFF03190C) else RadiantGreenPrimary,
                modifier = Modifier.size(15.dp)
            )
            Text(
                text = name,
                fontFamily = if (lang == AppLanguage.URDU) JameelNooriFontFamily else AmiriFontFamily,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                fontSize = if (lang == AppLanguage.URDU) 14.sp else 12.sp,
                color = if (isSelected) Color(0xFF03190C) else TextWhitePrimary
            )
            if (count != null) {
                Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = if (isSelected) Color(0x30000000) else Color(0x2834D399)
                ) {
                    Text(
                        text = "$count",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (isSelected) Color(0xFF03190C) else RadiantGreenPrimary,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 1.dp)
                    )
                }
            }
        }
    }
}

/**
 * Genre section block rendering a header and the books within this genre
 */
@Composable
private fun GenreSectionBlock(
    category: CategoryEntity,
    books: List<BookEntity>,
    viewMode: LibraryViewMode,
    lang: AppLanguage,
    onOpenBook: (BookEntity) -> Unit,
    onToggleFavorite: (BookEntity) -> Unit,
    onEdit: (BookEntity) -> Unit,
    onDelete: (BookEntity) -> Unit,
    onAddBookToGenre: () -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        GenreSectionHeader(
            title = Strings.localizedCategoryName(category.name, lang),
            description = getGenreDescription(category.name, lang),
            icon = getGenreIcon(category.name),
            booksCount = books.size,
            lang = lang,
            onAddBook = onAddBookToGenre
        )

        Spacer(modifier = Modifier.height(8.dp))

        if (books.isEmpty()) {
            EmptyGenreShelfPlaceholder(
                genreName = Strings.localizedCategoryName(category.name, lang),
                lang = lang,
                onAddBook = onAddBookToGenre
            )
        } else if (viewMode == LibraryViewMode.CATEGORIZED_SHELVES) {
            // Horizontal Shelf View
            BooksHorizontalShelf(
                books = books,
                lang = lang,
                onOpenBook = onOpenBook,
                onToggleFavorite = onToggleFavorite,
                onEdit = onEdit,
                onDelete = onDelete
            )
        } else {
            // Grid / Vertical View for this genre
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                books.forEach { book ->
                    BookCard(
                        book = book,
                        lang = lang,
                        onRead = { onOpenBook(book) },
                        onToggleFavorite = { onToggleFavorite(book) },
                        onEdit = { onEdit(book) },
                        onDelete = { onDelete(book) }
                    )
                }
            }
        }
    }
}

@Composable
private fun GenreSectionHeader(
    title: String,
    description: String,
    icon: ImageVector,
    booksCount: Int,
    lang: AppLanguage,
    onAddBook: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 2.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.weight(1f)
        ) {
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = Color(0x3522C55E),
                border = BorderStroke(1.dp, Color(0x604ADE80)),
                modifier = Modifier.size(36.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = RadiantGreenPrimary,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.width(10.dp))

            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = title,
                        fontFamily = if (lang == AppLanguage.URDU) JameelNooriFontFamily else AmiriFontFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = if (lang == AppLanguage.URDU) 19.sp else 17.sp,
                        color = TextWhitePrimary
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    // Count Badge
                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        color = Color(0x2822C55E),
                        border = BorderStroke(0.8.dp, Color(0x5034D399))
                    ) {
                        Text(
                            text = Strings.genreBooksLabel(booksCount, lang),
                            fontFamily = if (lang == AppLanguage.URDU) JameelNooriFontFamily else AmiriFontFamily,
                            fontSize = if (lang == AppLanguage.URDU) 12.sp else 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = RadiantGreenPrimary,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                        )
                    }
                }
                if (description.isNotBlank()) {
                    Text(
                        text = description,
                        fontFamily = if (lang == AppLanguage.URDU) JameelNooriFontFamily else AmiriFontFamily,
                        fontSize = if (lang == AppLanguage.URDU) 12.sp else 10.sp,
                        color = TextMutedGreen
                    )
                }
            }
        }

        // Add to this genre button (+)
        Surface(
            shape = CircleShape,
            color = Color(0x2834D399),
            border = BorderStroke(0.8.dp, Color(0x6034D399)),
            modifier = Modifier
                .size(32.dp)
                .clip(CircleShape)
                .clickable(onClick = onAddBook)
                .testTag("add_book_to_genre_$title")
        ) {
            Box(contentAlignment = Alignment.Center) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add book",
                    tint = RadiantGreenPrimary,
                    modifier = Modifier.size(16.dp)
                )
            }
        }
    }
}

/**
 * Horizontal shelf of books for a genre with a physical bookshelf visual aesthetic
 */
@Composable
private fun BooksHorizontalShelf(
    books: List<BookEntity>,
    lang: AppLanguage,
    onOpenBook: (BookEntity) -> Unit,
    onToggleFavorite: (BookEntity) -> Unit,
    onEdit: (BookEntity) -> Unit,
    onDelete: (BookEntity) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 4.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(books, key = { it.id }) { book ->
                LibraryShelfItemCard(
                    book = book,
                    lang = lang,
                    onRead = { onOpenBook(book) },
                    onToggleFavorite = { onToggleFavorite(book) },
                    onEdit = { onEdit(book) },
                    onDelete = { onDelete(book) }
                )
            }
        }

        // Luminous Bookshelf Rail Underneath
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 2.dp)
                .height(3.dp)
                .background(
                    Brush.horizontalGradient(
                        listOf(
                            Color.Transparent,
                            Color(0x6034D399),
                            RadiantGreenPrimary.copy(alpha = 0.8f),
                            Color(0x6034D399),
                            Color.Transparent
                        )
                    ),
                    RoundedCornerShape(2.dp)
                )
        )
    }
}

/**
 * Compact book card optimized for horizontal genre shelf display
 */
@Composable
private fun LibraryShelfItemCard(
    book: BookEntity,
    lang: AppLanguage,
    onRead: () -> Unit,
    onToggleFavorite: () -> Unit,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    var menuExpanded by remember { mutableStateOf(false) }

    Surface(
        shape = RoundedCornerShape(18.dp),
        color = Color(0x3812281D),
        border = BorderStroke(
            1.dp,
            if (book.isFavorite) Color(0x80FBBF24) else Color(0x4034D399)
        ),
        modifier = Modifier
            .width(260.dp)
            .clip(RoundedCornerShape(18.dp))
            .clickable(onClick = onRead)
            .testTag("shelf_book_${book.id}")
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                // Top row: Favorite star & Overflow menu
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Favorite Toggle
                    IconButton(
                        onClick = onToggleFavorite,
                        modifier = Modifier.size(28.dp)
                    ) {
                        Icon(
                            imageVector = if (book.isFavorite) Icons.Default.Star else Icons.Default.Bookmark,
                            contentDescription = "Favorite",
                            tint = if (book.isFavorite) Color(0xFFFBBF24) else TextMutedGreen,
                            modifier = Modifier.size(18.dp)
                        )
                    }

                    // Direct shamela badge
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = Color(0x2034D399)
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.OpenInNew,
                                contentDescription = null,
                                tint = RadiantGreenPrimary,
                                modifier = Modifier.size(10.dp)
                            )
                            Spacer(modifier = Modifier.width(3.dp))
                            Text(
                                text = "shamela.ws",
                                fontSize = 9.sp,
                                color = RadiantGreenPrimary
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(6.dp))

                // Book Title
                Text(
                    text = book.title,
                    fontFamily = if (lang == AppLanguage.URDU) JameelNooriFontFamily else AmiriFontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = if (lang == AppLanguage.URDU) 17.sp else 15.sp,
                    color = TextWhitePrimary,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    lineHeight = if (lang == AppLanguage.URDU) 24.sp else 20.sp
                )

                Spacer(modifier = Modifier.height(4.dp))

                // Author
                if (book.author.isNotBlank()) {
                    Text(
                        text = book.author,
                        fontFamily = if (lang == AppLanguage.URDU) JameelNooriFontFamily else AmiriFontFamily,
                        fontSize = if (lang == AppLanguage.URDU) 13.sp else 11.sp,
                        color = LuminousGreenText,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Bottom action row: Read button
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = onRead,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = RadiantGreenPrimary,
                        contentColor = Color(0xFF03190C)
                    ),
                    shape = RoundedCornerShape(12.dp),
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp),
                    modifier = Modifier.height(32.dp)
                ) {
                    Text(
                        text = Strings.readNow(lang),
                        fontFamily = if (lang == AppLanguage.URDU) JameelNooriFontFamily else AmiriFontFamily,
                        fontSize = if (lang == AppLanguage.URDU) 13.sp else 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                // Edit/Delete options
                Box {
                    IconButton(
                        onClick = { menuExpanded = true },
                        modifier = Modifier.size(28.dp)
                    ) {
                        Text(
                            text = "•••",
                            color = TextMutedGreen,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                    }

                    DropdownMenu(
                        expanded = menuExpanded,
                        onDismissRequest = { menuExpanded = false },
                        modifier = Modifier.background(Color(0xFF07190F))
                    ) {
                        DropdownMenuItem(
                            text = {
                                Text(
                                    text = Strings.editAction(lang),
                                    fontFamily = if (lang == AppLanguage.URDU) JameelNooriFontFamily else AmiriFontFamily,
                                    color = TextWhitePrimary
                                )
                            },
                            onClick = {
                                menuExpanded = false
                                onEdit()
                            }
                        )
                        DropdownMenuItem(
                            text = {
                                Text(
                                    text = Strings.deleteAction(lang),
                                    fontFamily = if (lang == AppLanguage.URDU) JameelNooriFontFamily else AmiriFontFamily,
                                    color = Color(0xFFF87171)
                                )
                            },
                            onClick = {
                                menuExpanded = false
                                onDelete()
                            }
                        )
                    }
                }
            }
        }
    }
}

/**
 * Placeholder when a genre has no books yet
 */
@Composable
private fun EmptyGenreShelfPlaceholder(
    genreName: String,
    lang: AppLanguage,
    onAddBook: () -> Unit
) {
    Surface(
        shape = RoundedCornerShape(16.dp),
        color = Color(0x18142E20),
        border = BorderStroke(0.8.dp, Color(0x3034D399)),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp)
    ) {
        Row(
            modifier = Modifier.padding(14.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = Strings.noBooksInGenre(lang),
                    fontFamily = if (lang == AppLanguage.URDU) JameelNooriFontFamily else AmiriFontFamily,
                    fontSize = 12.sp,
                    color = TextMutedGreen
                )
            }
            Button(
                onClick = onAddBook,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0x3034D399),
                    contentColor = RadiantGreenPrimary
                ),
                shape = RoundedCornerShape(12.dp),
                contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp),
                modifier = Modifier.height(30.dp)
            ) {
                Text(
                    text = Strings.addBook(lang),
                    fontFamily = if (lang == AppLanguage.URDU) JameelNooriFontFamily else AmiriFontFamily,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

/**
 * Search Bar styled with glass borders for library
 */
@Composable
private fun LibrarySearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    onClear: () -> Unit,
    lang: AppLanguage,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = RoundedCornerShape(16.dp),
        color = Color(0x35142E20),
        border = BorderStroke(1.dp, Color(0x5534D399)),
        modifier = modifier
    ) {
        OutlinedTextField(
            value = query,
            onValueChange = onQueryChange,
            placeholder = {
                Text(
                    text = Strings.searchHint(lang),
                    fontFamily = if (lang == AppLanguage.URDU) JameelNooriFontFamily else AmiriFontFamily,
                    fontSize = if (lang == AppLanguage.URDU) 15.sp else 13.sp,
                    color = TextMutedGreen
                )
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search",
                    tint = RadiantGreenPrimary,
                    modifier = Modifier.size(20.dp)
                )
            },
            trailingIcon = {
                if (query.isNotEmpty()) {
                    IconButton(onClick = onClear) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Clear",
                            tint = RadiantGreenPrimary,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            },
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent,
                focusedTextColor = TextWhitePrimary,
                unfocusedTextColor = TextWhitePrimary,
                cursorColor = RadiantGreenPrimary
            ),
            modifier = Modifier
                .fillMaxWidth()
                .testTag("library_search_input")
        )
    }
}

/**
 * Bottom navigation bar with direct switching between Home and Main Library
 */
@Composable
private fun LibraryBottomNavigationBar(
    currentScreen: ActiveAppScreen,
    lang: AppLanguage,
    onNavigateHome: () -> Unit,
    onNavigateLibrary: () -> Unit,
    onOpenFavorites: () -> Unit,
    onAddNewBook: () -> Unit
) {
    Surface(
        color = Color(0xF2060D08),
        border = BorderStroke(1.dp, Color(0x3534D399)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Home Tab
            val isHome = currentScreen == ActiveAppScreen.HOME
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .clip(RoundedCornerShape(14.dp))
                    .clickable(onClick = onNavigateHome)
                    .padding(horizontal = 12.dp, vertical = 4.dp)
                    .testTag("tab_home")
            ) {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = null,
                    tint = if (isHome) RadiantGreenPrimary else TextMutedGreen,
                    modifier = Modifier.size(22.dp)
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = Strings.homeTab(lang),
                    fontFamily = if (lang == AppLanguage.URDU) JameelNooriFontFamily else AmiriFontFamily,
                    fontSize = if (lang == AppLanguage.URDU) 13.sp else 11.sp,
                    fontWeight = if (isHome) FontWeight.Bold else FontWeight.Normal,
                    color = if (isHome) LuminousGreenText else TextMutedGreen
                )
            }

            // Center: Elevated curved pill button "المكتبة" (Library)
            val isLibrary = currentScreen == ActiveAppScreen.MAIN_LIBRARY
            Surface(
                shape = RoundedCornerShape(26.dp),
                color = if (isLibrary) RadiantGreenPrimary else Color(0x35142E20),
                border = BorderStroke(1.dp, if (isLibrary) Color(0x9986EFAC) else Color(0x4034D399)),
                modifier = Modifier
                    .clip(RoundedCornerShape(26.dp))
                    .clickable(onClick = onNavigateLibrary)
                    .testTag("tab_main_library")
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 18.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.MenuBook,
                        contentDescription = null,
                        tint = if (isLibrary) Color(0xFF03190C) else RadiantGreenPrimary,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = Strings.libraryTab(lang),
                        fontFamily = AmiriFontFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp,
                        color = if (isLibrary) Color(0xFF03190C) else RadiantGreenPrimary
                    )
                }
            }

            // Favorites Tab
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .clip(RoundedCornerShape(14.dp))
                    .clickable(onClick = onOpenFavorites)
                    .padding(horizontal = 12.dp, vertical = 4.dp)
                    .testTag("tab_favorites")
            ) {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = null,
                    tint = TextMutedGreen,
                    modifier = Modifier.size(22.dp)
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = Strings.favorites(lang),
                    fontFamily = if (lang == AppLanguage.URDU) JameelNooriFontFamily else AmiriFontFamily,
                    fontSize = if (lang == AppLanguage.URDU) 13.sp else 11.sp,
                    color = TextMutedGreen
                )
            }
        }
    }
}

private fun getGenreIcon(genreName: String): ImageVector {
    return when {
        genreName.contains("حنفي") || genreName.contains("Fiqh") || genreName.contains("فقه") -> Icons.Default.MenuBook
        genreName.contains("Tafsir") || genreName.contains("تفسير") -> Icons.Default.AutoAwesome
        genreName.contains("Hadith") || genreName.contains("حديث") -> Icons.Default.CollectionsBookmark
        genreName.contains("Aqidah") || genreName.contains("عقيد") -> Icons.Default.Bookmark
        genreName.contains("Seerah") || genreName.contains("سير") || genreName.contains("History") || genreName.contains("تاريخ") -> Icons.Default.LocalLibrary
        genreName.contains("Arabic") || genreName.contains("عرب") -> Icons.Default.MenuBook
        genreName.contains("AI") || genreName.contains("ذكاء") -> Icons.Default.AutoAwesome
        else -> Icons.Default.MenuBook
    }
}

private fun getGenreDescription(genreName: String, lang: AppLanguage): String {
    return when {
        genreName.contains("حنفي") -> if (lang == AppLanguage.URDU) "فقہ حنفی کے اہم متون، شروح اور فتاویٰ" else "أمهات كتب ومتون وشروح الفقه الحنفي المعتمدة"
        genreName.contains("Tafsir") || genreName.contains("تفسير") -> if (lang == AppLanguage.URDU) "تفاسیر قرآن کریم اور علوم القرآن" else "أمهات تفاسير القرآن العظيم ومصادره"
        genreName.contains("Hadith") || genreName.contains("حديث") -> if (lang == AppLanguage.URDU) "صحاح ستہ، مسانید اور جوامع حدیث" else "الصحاح والسنن والمسانيد وكتب السنة النبوية"
        genreName.contains("Fiqh") || genreName.contains("فقه") -> if (lang == AppLanguage.URDU) "فقہ عام اور قواعد فقہیہ" else "الفقه الإسلامي المقارن وأصول الفقه والقواعد"
        genreName.contains("Aqidah") || genreName.contains("عقيد") -> if (lang == AppLanguage.URDU) "عقائد اہل سنت و کلام اسلامی" else "كتب العقيدة الإسلامية وأصول الإيمان"
        genreName.contains("Seerah") || genreName.contains("سير") || genreName.contains("History") || genreName.contains("تاريخ") -> if (lang == AppLanguage.URDU) "سیرت طیبہ اور تاریخ اسلام" else "السيرة النبوية العطرة والتاريخ والتراجم"
        genreName.contains("Arabic") || genreName.contains("عرب") -> if (lang == AppLanguage.URDU) "نحو، صرف اور عربی بلاغت" else "علوم النحو والصرف والبلاغة والمعاجم"
        genreName.contains("AI") || genreName.contains("ذكاء") -> if (lang == AppLanguage.URDU) "جدید تحقیقات اور فتاویٰ" else "الخدمات الذكية والبحوث والفتاوى"
        else -> ""
    }
}
