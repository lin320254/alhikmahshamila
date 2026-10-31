package com.example

import com.example.ui.ArabicSearchUtils
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class ExampleUnitTest {
    @Test
    fun testSearchMatchesTitle() {
        val title = "رد المحتار على الدر المختار (حاشية ابن عابدين)"
        val author = "محمد أمين بن عمر (ابن عابدين الدمشقي)"

        // Exact match
        assertTrue(ArabicSearchUtils.matches("المحتار", title, author))

        // Without diacritics / with alef variation
        assertTrue(ArabicSearchUtils.matches("رد المحتار", title, author))
    }

    @Test
    fun testSearchMatchesAuthor() {
        val title = "الهداية في شرح بداية المبتدي"
        val author = "علي بن أبي بكر بن عبد الجليل المرغيناني"

        // Author name search
        assertTrue(ArabicSearchUtils.matches("المرغيناني", title, author))
        assertTrue(ArabicSearchUtils.matches("المرغينانی", title, author))
        assertTrue(ArabicSearchUtils.matches("علي بن ابي بكر", title, author))
    }

    @Test
    fun testSearchDiacriticsAndHamzaInsensitive() {
        val title = "مُخْتَصَرُ القُدُورِيِّ فِي الفِقْهِ الحَنَفِيِّ"
        val author = "أَبُو الحُسَيْنِ أَحْمَدُ بنُ مُحَمَّدِ بنِ أَحْمَدَ القُدُورِيُّ"

        // Search typed without any vowel markings/harakat
        assertTrue(ArabicSearchUtils.matches("مختصر القدوري", title, author))
        assertTrue(ArabicSearchUtils.matches("القدوري", title, author))
        assertTrue(ArabicSearchUtils.matches("ابو الحسين", title, author))
        assertTrue(ArabicSearchUtils.matches("الفقه الحنفي", title, author))
    }

    @Test
    fun testMultiTokenSearchAcrossTitleAndAuthor() {
        val title = "رد المحتار على الدر المختار"
        val author = "ابن عابدين"

        // One token matches author, one token matches title
        assertTrue(ArabicSearchUtils.matches("ابن عابدين الدر", title, author))

        // Unrelated query returns false
        assertFalse(ArabicSearchUtils.matches("رياض الصالحين", title, author))
    }
}
