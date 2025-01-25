package com.example.smartnoteapp.notes.presentation.home_notes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.smartnoteapp.notes.domain.models.Note
import com.example.smartnoteapp.notes.domain.usecases.notes.GetNotePagingDataUseCase
import com.example.smartnoteapp.core.presentation.states.UiState
import com.example.smartnoteapp.notes.data.models.remote.NoteRemote
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getNotePagingDataUseCase: GetNotePagingDataUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<UiState<PagingData<NoteRemote>>>(UiState.Empty())
    val state: StateFlow<UiState<PagingData<NoteRemote>>> = _state.asStateFlow()

    init {
        getNotes()
    }

    fun updateState(newState: UiState<PagingData<NoteRemote>>){
        _state.value = newState
    }

    fun getNotes() {
        viewModelScope.launch {
            try {
                updateState(UiState.Loading())
                delay(1500)

                getNotePagingDataUseCase()
                    .flow
                    .cachedIn(viewModelScope)
                    .collect { pagingData ->
                        updateState(UiState.Success(pagingData))
                    }
            } catch (e: Exception) {
                updateState(UiState.Error(e.message ?: "Unknown Error"))
            }
        }
    }
}

