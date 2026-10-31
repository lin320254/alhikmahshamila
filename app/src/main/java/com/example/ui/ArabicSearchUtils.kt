package com.example.ui

/**
 * Intelligent real-time search utility for Arabic and Urdu library contents.
 * Handles diacritic removal (Tashkeel/Harakat), tatweel removal, and Unicode character
 * normalization (Alef, Teh Marbuta, Yeh, Waw, Kaf) so user queries match titles and author
 * names effortlessly regardless of vowel markings or spelling variations.
 */
object ArabicSearchUtils {
    // Harakat (diacritics: fathah, dammah, kasrah, sukoon, shaddah, etc.), Quranic annotations, and Tatweel
    private val DIACRITICS_REGEX = Regex("[\u064B-\u065F\u0670\u06D6-\u06ED\u0640]")

    /**
     * Normalizes an Arabic or Urdu text string for robust search matching:
     * - Strips diacritics / harakat and tatweel
     * - Normalizes alef forms (أ, إ, آ, ٱ -> ا)
     * - Normalizes teh marbuta and heh (ة, ۃ, ھ -> ه)
     * - Normalizes yeh variants (ي, ى, ی, ے -> ي)
     * - Normalizes waw variants (ؤ -> و)
     * - Normalizes kaf variants (ك, ک, ڪ -> ك)
     * - Trims and lowercases ASCII characters
     */
    fun normalize(text: String): String {
        if (text.isEmpty()) return ""
        return text
            .replace(DIACRITICS_REGEX, "")
            .replace(Regex("[أإآٱ]"), "ا")
            .replace(Regex("[ةۃھ]"), "ه")
            .replace(Regex("[يىیے]"), "ي")
            .replace(Regex("[ؤ]"), "و")
            .replace(Regex("[كکڪ]"), "ك")
            .trim()
            .lowercase()
    }

    /**
     * Checks if a user search query matches against any of the target fields (title, author, etc.)
     * Supports multi-token queries where every typed token must be found in at least one of the fields.
     */
    fun matches(query: String, vararg targetFields: String): Boolean {
        val normQuery = normalize(query)
        if (normQuery.isBlank()) return true

        val tokens = normQuery.split(Regex("\\s+")).filter { it.isNotBlank() }
        if (tokens.isEmpty()) return true

        val normalizedTargets = targetFields.map { normalize(it) }

        // Every token typed by user must match at least one of the target fields
        return tokens.all { token ->
            normalizedTargets.any { target -> target.contains(token) }
        }
    }
}
