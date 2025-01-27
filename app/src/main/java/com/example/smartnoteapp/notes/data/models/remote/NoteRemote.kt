package com.example.smartnoteapp.notes.data.models.remote

data class NoteRemote (
    val id: Long = 0,
    val title: String = "",
    val description: String = "",
    val timestamp: Long = 0,
    val image: String = "",
    val likesCount: Int = 0,
    val commentsCount: Int = 0,
    val categories: List<String>? = null,
    val likedUsers: List<String>? = null
)