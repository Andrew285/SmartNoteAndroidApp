package com.example.smartnoteapp.videos.data.models

data class VideoEntity (
    val id: Long,
    val title: String,
    val description: String,
    val videoUrl: String,
    val timestamp: Long,
    val likesCount: Int,
    val commentsCount: Int
)