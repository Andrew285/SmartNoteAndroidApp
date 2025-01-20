package com.example.smartnoteapp.notes.data.models

import androidx.room.Entity

@Entity(
    tableName = "note_categories_refs",
    primaryKeys = ["noteId", "categoryId"]
)
data class NoteCategoryCrossRef (
    val noteId: Long,
    val categoryId: Long
)