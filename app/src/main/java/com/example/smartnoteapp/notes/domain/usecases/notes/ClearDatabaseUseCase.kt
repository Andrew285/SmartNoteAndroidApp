package com.example.smartnoteapp.notes.domain.usecases.notes

import com.example.smartnoteapp.notes.domain.repository.INoteRepository
import javax.inject.Inject

class ClearDatabaseUseCase @Inject constructor(
    private val noteRepository: INoteRepository
) {
    suspend operator fun invoke() {
        noteRepository.clearDatabase()
    }
}