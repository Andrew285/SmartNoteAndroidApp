package com.example.smartnoteapp.core.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.smartnoteapp.notes.data.database.NoteDao
import com.example.smartnoteapp.notes.data.models.NoteEntity

@Database(entities = [NoteEntity::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun noteDao(): NoteDao
}