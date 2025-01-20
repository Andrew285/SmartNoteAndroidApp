package com.example.smartnoteapp.core.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.smartnoteapp.notes.data.database.NoteDao
import com.example.smartnoteapp.notes.data.models.CategoryEntity
import com.example.smartnoteapp.notes.data.models.NoteCategoryCrossRef
import com.example.smartnoteapp.notes.data.models.NoteEntity

@Database(entities = [NoteEntity::class, CategoryEntity::class, NoteCategoryCrossRef::class], version = 2)
abstract class AppDatabase: RoomDatabase() {
    abstract fun noteDao(): NoteDao
}