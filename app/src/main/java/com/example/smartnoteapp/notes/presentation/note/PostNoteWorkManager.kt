package com.example.smartnoteapp.notes.presentation.note

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.smartnoteapp.notes.domain.usecases.local_notes.GetNoteByIdUseCase
import com.example.smartnoteapp.notes.domain.usecases.remote_notes.PostNoteUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class PostNoteWorkManager @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParameters: WorkerParameters,
    val postNoteUseCase: PostNoteUseCase,
    val getNoteByIdUseCase: GetNoteByIdUseCase
    ): CoroutineWorker(context, workerParameters) {
    companion object {
        const val INPUT_DATA_NOTE = "noteData"
    }

    override suspend fun doWork(): Result {
        return try {
            val noteId = inputData.getLong(INPUT_DATA_NOTE, -1)
            if (noteId != -1L) {
                val note = getNoteByIdUseCase(noteId)
                postNoteUseCase(note)
                Result.success()
            }
            else {
                Result.failure()
            }
        } catch (e: Exception) {
            // Log the error message for easier debugging
            Log.e("PostNoteWorkManager", "Error in doWork: ${e.message}")
            Result.failure()
        }
    }
}