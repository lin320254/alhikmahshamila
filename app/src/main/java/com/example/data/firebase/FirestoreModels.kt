package com.example.data.firebase

import com.google.firebase.Timestamp
import com.google.firebase.firestore.IgnoreExtraProperties
import com.google.firebase.firestore.ServerTimestamp

@IgnoreExtraProperties
data class UserProfile(
    val userId: String = "",
    val displayName: String = "",
    val email: String = "",
    @ServerTimestamp val createdAt: Timestamp? = null,
    @ServerTimestamp val lastLoginAt: Timestamp? = null
)

@IgnoreExtraProperties
data class UserBookFirestore(
    val id: String = "",
    val userId: String = "",
    val title: String = "",
    val author: String = "",
    val category: String = "",
    val url: String = "",
    val notes: String = "",
    val isFavorite: Boolean = false,
    @ServerTimestamp val createdAt: Timestamp? = null,
    @ServerTimestamp val updatedAt: Timestamp? = null
)
