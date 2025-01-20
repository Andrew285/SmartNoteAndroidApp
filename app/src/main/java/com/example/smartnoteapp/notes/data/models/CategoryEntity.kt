package com.example.smartnoteapp.notes.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "categories")
data class CategoryEntity (
    @PrimaryKey val id: Long,
    val name: String
)