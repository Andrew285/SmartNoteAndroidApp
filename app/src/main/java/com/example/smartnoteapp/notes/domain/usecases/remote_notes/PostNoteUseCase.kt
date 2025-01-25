package com.example.smartnoteapp.notes.domain.usecases.remote_notes

import android.util.Log
import com.example.smartnoteapp.notes.domain.models.Note
import com.example.smartnoteapp.notes.domain.repository.INoteRemoteRepository
import javax.inject.Inject

class PostNoteUseCase @Inject constructor(
    private val noteRemoteRepository: INoteRemoteRepository
) {
    suspend operator fun invoke(note: Note): Result<Unit> {
        return try {
            noteRemoteRepository.addNote(note)
        } catch (e: Exception) {
            Log.e("PostNoteUseCase", "Error adding note: ${e.message}")
            Result.failure(e) // You might want to represent failure properly
        }
    }
}