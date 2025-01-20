package com.example.smartnoteapp.notes.domain.usecases.notes

import com.example.smartnoteapp.notes.domain.models.Note
import com.example.smartnoteapp.notes.domain.repository.INoteRemoteRepository
import javax.inject.Inject

class PostNoteUseCase @Inject constructor(
    private val noteRemoteRepository: INoteRemoteRepository
) {
    suspend operator fun invoke(note: Note) {
        noteRemoteRepository.addNote(note)
    }
}