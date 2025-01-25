package com.example.smartnoteapp.notes.presentation.note

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.example.smartnoteapp.R
import com.example.smartnoteapp.notes.domain.models.Note
import com.example.smartnoteapp.notes.domain.usecases.local_notes.DeleteNoteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class OwnNoteViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val deleteNoteUseCase: DeleteNoteUseCase
): ViewModel() {

    private val _notePostState = MutableStateFlow<PostState>(PostState.Loading)
    val notePostState: StateFlow<PostState> = _notePostState

    fun updateNotePostState(newState: PostState) {
        _notePostState.value = newState
    }

    fun postNote(note: Note, timeToDelay: Int) {
        updateNotePostState(PostState.Loading)

//        val noteJson = Gson().toJson(note)
        val noteData = workDataOf(PostNoteWorkManager.INPUT_DATA_NOTE to note.id)

        val postWorkRequest = OneTimeWorkRequestBuilder<PostNoteWorkManager>()
            .setInputData(noteData)
            .setInitialDelay(timeToDelay.toLong(), TimeUnit.SECONDS)
            .build()

        WorkManager.getInstance(context).enqueue(postWorkRequest)
        WorkManager.getInstance(context).getWorkInfoByIdLiveData(postWorkRequest.id).observeForever { workInfo ->
            when (workInfo?.state) {
                WorkInfo.State.SUCCEEDED -> {
                    updateNotePostState(PostState.Posted)
                }
                WorkInfo.State.RUNNING -> {
                    updateNotePostState(PostState.Delayed(context.getString(R.string.note_delayed, timeToDelay)))
                }
                WorkInfo.State.FAILED -> {
                    updateNotePostState(PostState.Failed(context.getString(R.string.note_was_not_posted)))
                    Log.e("WorkManager", "Work Failed: ${workInfo.state}") // Log the failure
                }
                else -> { }
            }
        }
    }

    fun deleteNote(noteId: Long) {
        viewModelScope.launch {
            deleteNoteUseCase(noteId)
        }
    }
}