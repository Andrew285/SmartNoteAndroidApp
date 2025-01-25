package com.example.smartnoteapp.notes.domain.usecases.local_notes

import com.example.smartnoteapp.notes.domain.models.Note
import com.example.smartnoteapp.notes.domain.repository.INoteRepository
import javax.inject.Inject

class GetAllNotesUseCase @Inject constructor(
    private val noteRepository: INoteRepository
) {
    suspend operator fun invoke(): List<Note> {
        return noteRepository.getAllNotes()
    }
}