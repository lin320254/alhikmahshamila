package com.example.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "books")
data class BookEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val author: String = "",
    val category: String,
    val url: String,
    val notes: String = "",
    val isFavorite: Boolean = false,
    val createdAt: Long = System.currentTimeMillis(),
    val lastOpenedAt: Long? = null
)
