package com.example.smartnoteapp.notes.domain.repository

import com.example.smartnoteapp.notes.domain.models.Note

interface INoteRepository {
    suspend fun addNote(note: Note)
    suspend fun editNote(note: Note)
    suspend fun deleteNote(noteId: Long)
    suspend fun deleteAllNotes()
    suspend fun deleteAllNoteCategoryRefs()
    suspend fun deleteAllCategories()
    suspend fun clearDatabase()
    suspend fun getAllNotes(): List<Note>
    suspend fun getNoteById(noteId: Long): Note
}