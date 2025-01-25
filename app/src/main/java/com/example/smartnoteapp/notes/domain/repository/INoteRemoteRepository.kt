package com.example.smartnoteapp.notes.domain.repository

import androidx.paging.Pager
import com.example.smartnoteapp.notes.data.models.remote.NoteRemote
import com.example.smartnoteapp.notes.domain.models.Note

interface INoteRemoteRepository {

    suspend fun addNote(note: Note): Result<Unit>

    suspend fun editNote(note: Note): Result<NoteRemote?>

    suspend fun deleteNote(noteId: Int): Result<NoteRemote?>

    suspend fun getNoteById(noteId: Int): NoteRemote

    suspend fun getAllNotes(): List<Note>

    suspend fun getNotesPagingData(): Pager<Int, NoteRemote>
}