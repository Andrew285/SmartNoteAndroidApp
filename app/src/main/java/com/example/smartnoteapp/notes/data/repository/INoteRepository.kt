package com.example.smartnoteapp.notes.data.repository

import com.example.smartnoteapp.notes.domain.models.Note

interface INoteRepository {
    suspend fun createNote(note: Note)
    suspend fun editNote(note: Note)
    suspend fun deleteNote(noteId: Int)
    suspend fun getAllNotes(): List<Note>
    suspend fun getNoteById(noteId: Int): Note?
}