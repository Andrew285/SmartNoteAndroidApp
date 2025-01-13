package com.example.smartnoteapp.notes.data.repository

import com.example.smartnoteapp.core.App
import com.example.smartnoteapp.notes.data.database.NoteDao
import com.example.smartnoteapp.notes.data.models.NoteEntity
import com.example.smartnoteapp.notes.domain.models.Note

class NoteRepository(
    private val noteDao: NoteDao
): INoteRepository {
    override suspend fun createNote(note: Note) {
        noteDao.insert(note)
    }

    override suspend fun editNote(note: Note) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteNote(noteId: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun getAllNotes(): List<Note> {
        TODO("Not yet implemented")
    }

    override suspend fun getNoteById(noteId: Int): Note? {
        TODO("Not yet implemented")
    }

    private fun mapNoteToEntity(note: Note): NoteEntity {
        return NoteEntity(
            id = note.id,
            title = note.title,
            description = note.description,
            timestamp = note.timestamp,
            categoryIds = note.category?.id
        )
    }

    private fun mapEntityToNote(entity: NoteEntity): Note {
        return Note(
            id = entity.id,
            title = entity.title,
            content = entity.content,
            timestamp = entity.timestamp,
            category = entity.categoryId?.let { Category(it, "Category Name") } // Simplified for example
        )
    }
}