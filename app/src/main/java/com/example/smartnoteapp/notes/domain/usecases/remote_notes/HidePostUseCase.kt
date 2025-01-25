package com.example.smartnoteapp.notes.domain.usecases.remote_notes

import com.example.smartnoteapp.notes.data.models.remote.NoteRemote
import com.example.smartnoteapp.notes.domain.repository.INoteRemoteRepository
import javax.inject.Inject

class HidePostUseCase @Inject constructor(
    private val noteRemoteRepository: INoteRemoteRepository
) {
    suspend operator fun invoke(id: Int): Result<NoteRemote?> {
        return noteRemoteRepository.deleteNote(id)
    }
}