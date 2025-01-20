package com.example.smartnoteapp.notes.data.repository

import android.util.Log
import com.example.smartnoteapp.notes.data.database.NoteDao
import com.example.smartnoteapp.notes.data.models.NoteCategoryCrossRef
import com.example.smartnoteapp.notes.data.models.converters.Converters.mapToNote
import com.example.smartnoteapp.notes.data.models.converters.Converters.mapToNoteEntity
import com.example.smartnoteapp.notes.data.models.converters.Converters.mapToNoteWithCategories
import com.example.smartnoteapp.notes.domain.models.Note
import com.example.smartnoteapp.notes.domain.repository.INoteRepository

class NoteRepository(
    private val noteDao: NoteDao
): INoteRepository {
    override suspend fun addNote(note: Note) {
        val noteWithCategories = note.mapToNoteWithCategories()
        val noteEntity = noteWithCategories.note
        val categories = noteWithCategories.categories

        val noteId = noteDao.insertNote(noteEntity)
        categories.forEach {
            val categoryId = noteDao.insertCategory(it)

            val noteCategoryCrossRef = NoteCategoryCrossRef(
                noteId = noteId,
                categoryId = categoryId
            )
            noteDao.insertNoteCategoryCrossRef(noteCategoryCrossRef)
        }
    }

    override suspend fun editNote(note: Note) {
        val noteEntity = note.mapToNoteEntity()
        noteDao.updateNote(noteEntity)
    }

    override suspend fun deleteNote(noteId: Long) {
        noteDao.deleteNote(noteId)
    }

    override suspend fun deleteAllNotes() {
        noteDao.deleteAllNotes()
    }

    override suspend fun deleteAllNoteCategoryRefs() {
        noteDao.deleteAllNoteCategoryRefs()
    }

    override suspend fun deleteAllCategories() {
        noteDao.deleteAllCategories()
    }

    override suspend fun clearDatabase() {
        deleteAllNoteCategoryRefs()
        deleteAllNotes()
        deleteAllCategories()
    }

    override suspend fun getAllNotes(): List<Note> {
        val notesWithCategories = noteDao.getAllNotesWitCategories()
        notesWithCategories.forEach { noteWithCategories ->
            Log.d("NotesWithCategories", "Note: ${noteWithCategories.note.title}, Categories: ${noteWithCategories.categories.map { it.name }}")
        }
        return notesWithCategories.map { it.mapToNote() }
    }

    override suspend fun getNoteById(noteId: Long): Note {
        return noteDao.getNoteWithCategoriesById(noteId).mapToNote()
    }
}