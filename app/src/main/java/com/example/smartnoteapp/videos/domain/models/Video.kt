package com.example.smartnoteapp.videos.domain.models

data class Video (
    val id: Long = 0,
    val title: String = "",
    val description: String = "",
    val videoUrl: String = "",
    val timestamp: Long = 0,
    val likesCount: Int = 0,
    val commentsCount: Int = 0
)