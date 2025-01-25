package com.example.smartnoteapp.notes.domain.usecases.remote_notes

import androidx.paging.Pager
import com.example.smartnoteapp.notes.data.models.remote.NoteRemote
import com.example.smartnoteapp.notes.domain.repository.INoteRemoteRepository
import javax.inject.Inject

class GetNotePagingDataUseCase @Inject constructor(
    private val noteRemoteRepository: INoteRemoteRepository
) {
    suspend operator fun invoke(): Pager<Int, NoteRemote> {
        return noteRemoteRepository.getNotesPagingData()
    }
}