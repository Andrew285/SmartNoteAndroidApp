package com.example.smartnoteapp.notes.presentation.posted_note

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartnoteapp.notes.domain.usecases.remote_notes.HidePostUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostedNoteViewModel @Inject constructor(
    private val hidePostUseCase: HidePostUseCase
): ViewModel() {

    private val _postedNoteState: MutableStateFlow<PostedNoteState> = MutableStateFlow(PostedNoteState.Empty)
    val postedNoteState: StateFlow<PostedNoteState> = _postedNoteState

    fun updatePostedNoteState(newState: PostedNoteState) {
        _postedNoteState.value = newState
    }

    fun hidePost(noteId: Int) {
        viewModelScope.launch {
            hidePostUseCase(noteId)
        }
    }
}