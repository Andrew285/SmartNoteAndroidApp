package com.example.smartnoteapp.notes.domain.usecases.local_notes

import com.example.smartnoteapp.notes.domain.models.Note
import com.example.smartnoteapp.notes.domain.repository.INoteRepository
import javax.inject.Inject

class GetNoteByIdUseCase @Inject constructor(
    private val noteRepository: INoteRepository
) {
    suspend operator fun invoke(noteId: Long): Note {
        return noteRepository.getNoteById(noteId)
    }
}