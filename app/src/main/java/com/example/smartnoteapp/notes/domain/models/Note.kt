package com.example.smartnoteapp.notes.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Note (
    val id: Long = 0,
    val title: String,
    val description: String,
    val timestamp: Long,
    val image: String,
    val likesCount: Int = 0,
    val commentsCount: Int = 0,
    val categories: List<Category>
): Parcelable