package com.example.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface BookDao {
    @Query("SELECT * FROM books ORDER BY isFavorite DESC, id ASC")
    fun getAllBooks(): Flow<List<BookEntity>>

    @Query("SELECT * FROM books")
    suspend fun getAllBooksList(): List<BookEntity>

    @Query("SELECT * FROM books WHERE category = :category ORDER BY isFavorite DESC, id ASC")
    fun getBooksByCategory(category: String): Flow<List<BookEntity>>

    @Query("SELECT * FROM books WHERE isFavorite = 1 ORDER BY id ASC")
    fun getFavoriteBooks(): Flow<List<BookEntity>>

    @Query("SELECT * FROM books WHERE title LIKE '%' || :query || '%' OR author LIKE '%' || :query || '%' OR category LIKE '%' || :query || '%' OR notes LIKE '%' || :query || '%' ORDER BY isFavorite DESC, id ASC")
    fun searchBooks(query: String): Flow<List<BookEntity>>

    @Query("SELECT * FROM books WHERE id = :id")
    suspend fun getBookById(id: Long): BookEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBook(book: BookEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBooks(books: List<BookEntity>)

    @Update
    suspend fun updateBook(book: BookEntity)

    @Delete
    suspend fun deleteBook(book: BookEntity)

    @Query("UPDATE books SET isFavorite = :isFav WHERE id = :id")
    suspend fun setFavorite(id: Long, isFav: Boolean)

    @Query("UPDATE books SET lastOpenedAt = :timestamp WHERE id = :id")
    suspend fun updateLastOpened(id: Long, timestamp: Long)

    @Query("SELECT COUNT(*) FROM books")
    suspend fun getBookCount(): Int

    @Query("DELETE FROM books WHERE category = :category")
    suspend fun deleteBooksByCategory(category: String)
}
