package com.example.smartnoteapp.notes.domain.repository

import androidx.paging.Pager
import com.example.smartnoteapp.notes.data.models.remote.NoteRemote
import com.example.smartnoteapp.notes.domain.models.Note

interface INoteRemoteRepository {

    suspend fun addNote(note: Note)

    suspend fun editNote(note: Note)

    suspend fun deleteNote(noteId: Int)

    suspend fun getNoteById(noteId: Int): Note

    suspend fun getAllNotes(): List<Note>

    suspend fun getNotesPagingData(): Pager<Int, NoteRemote>
}