package com.example.smartnoteapp.notes.domain.usecases.local_notes

import com.example.smartnoteapp.notes.domain.repository.INoteRepository
import com.example.smartnoteapp.notes.domain.models.Note
import javax.inject.Inject

class AddNoteUseCase @Inject constructor(
    private val noteRepository: INoteRepository
) {
    suspend operator fun invoke(note: Note) {
        noteRepository.addNote(note)
    }
}