package com.example.smartnoteapp.notes.domain.models

data class Note (
    val id: Int = 0,
    val title: String,
    val description: String,
    val timestamp: Long,
    val categoryIds: List<Int>
)