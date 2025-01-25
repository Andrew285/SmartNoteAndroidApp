package com.example.smartnoteapp.notes.presentation.note

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.example.smartnoteapp.notes.domain.usecases.local_notes.GetNoteByIdUseCase
import com.example.smartnoteapp.notes.domain.usecases.remote_notes.PostNoteUseCase
import javax.inject.Inject

class MyWorkerFactory @Inject constructor(
    private val postNoteUseCase: PostNoteUseCase,
    private val getNoteByIdUseCase: GetNoteByIdUseCase
) : WorkerFactory() {

    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker? {
        return when (workerClassName) {
            PostNoteWorkManager::class.java.name -> {
                // Using Hilt to provide the dependencies along with the context and parameters.
                PostNoteWorkManager(
                    context = appContext,
                    workerParameters = workerParameters,
                    postNoteUseCase = postNoteUseCase,
                    getNoteByIdUseCase = getNoteByIdUseCase
                )
            }
            else -> null // Handle other workers or return null
        }
    }
}