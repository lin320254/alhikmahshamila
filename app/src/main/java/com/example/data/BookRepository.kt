package com.example.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class BookRepository(
    private val bookDao: BookDao,
    private val categoryDao: CategoryDao
) {
    val allBooks: Flow<List<BookEntity>> = bookDao.getAllBooks()
    val allCategories: Flow<List<CategoryEntity>> = categoryDao.getAllCategories()
    val favoriteBooks: Flow<List<BookEntity>> = bookDao.getFavoriteBooks()

    suspend fun ensureDefaultDataPopulated() = withContext(Dispatchers.IO) {
        // 1. Ensure all default categories are present
        DefaultData.initialCategories.forEach { defaultCat ->
            if (categoryDao.getCategoryByName(defaultCat.name) == null) {
                categoryDao.insertCategory(defaultCat)
            }
        }

        // 2. Proactively update/clean default books and replace broken links with verified links
        val existingBooks = bookDao.getAllBooksList()
        val defaultUrls = DefaultData.initialBooks.map { it.url }.toSet()

        // Update any old/broken default books to the verified URLs
        existingBooks.filter { it.category == "فقہ حنفی" && it.url !in defaultUrls }.forEach { oldBook ->
            val matchingDefault = DefaultData.initialBooks.find { it.title.trim() == oldBook.title.trim() }
            if (matchingDefault != null) {
                bookDao.updateBook(
                    oldBook.copy(
                        url = matchingDefault.url,
                        author = matchingDefault.author,
                        notes = matchingDefault.notes
                    )
                )
            } else if (oldBook.url.startsWith("https://shamela.ws/book/")) {
                bookDao.deleteBook(oldBook)
            }
        }

        // 3. Ensure all verified default books are present
        val currentBooks = bookDao.getAllBooksList()
        val currentUrls = currentBooks.map { it.url }.toSet()
        val missingBooks = DefaultData.initialBooks.filter { it.url !in currentUrls }
        if (missingBooks.isNotEmpty()) {
            bookDao.insertBooks(missingBooks)
        }
    }

    fun getBooksByCategory(category: String): Flow<List<BookEntity>> {
        return bookDao.getBooksByCategory(category)
    }

    fun searchBooks(query: String): Flow<List<BookEntity>> {
        return bookDao.searchBooks(query)
    }

    suspend fun getBookById(id: Long): BookEntity? = withContext(Dispatchers.IO) {
        bookDao.getBookById(id)
    }

    suspend fun insertBook(book: BookEntity): Long = withContext(Dispatchers.IO) {
        bookDao.insertBook(book)
    }

    suspend fun updateBook(book: BookEntity) = withContext(Dispatchers.IO) {
        bookDao.updateBook(book)
    }

    suspend fun deleteBook(book: BookEntity) = withContext(Dispatchers.IO) {
        bookDao.deleteBook(book)
    }

    suspend fun setFavorite(id: Long, isFavorite: Boolean) = withContext(Dispatchers.IO) {
        bookDao.setFavorite(id, isFavorite)
    }

    suspend fun updateLastOpened(id: Long, timestamp: Long = System.currentTimeMillis()) = withContext(Dispatchers.IO) {
        bookDao.updateLastOpened(id, timestamp)
    }

    suspend fun insertCategory(category: CategoryEntity): Long = withContext(Dispatchers.IO) {
        categoryDao.insertCategory(category)
    }

    suspend fun deleteCategory(category: CategoryEntity) = withContext(Dispatchers.IO) {
        categoryDao.deleteCategory(category)
    }
}
