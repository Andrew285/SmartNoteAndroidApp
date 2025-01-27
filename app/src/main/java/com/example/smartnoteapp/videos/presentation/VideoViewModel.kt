package com.example.smartnoteapp.videos.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.smartnoteapp.core.presentation.states.UiState
import com.example.smartnoteapp.videos.domain.models.Video
import com.example.smartnoteapp.videos.domain.usecases.GetVideosPagingUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VideoViewModel @Inject constructor(
    private val getVideosPagingUseCase: GetVideosPagingUseCase
): ViewModel() {
    private val _state = MutableStateFlow<UiState<PagingData<Video>>>(UiState.Empty())
    val state: StateFlow<UiState<PagingData<Video>>> = _state.asStateFlow()

    init {
        getVideos()
    }

    fun updateState(newState: UiState<PagingData<Video>>){
        _state.value = newState
    }

    fun getVideos() {
        viewModelScope.launch {
            try {
                updateState(UiState.Loading())
                delay(1500)

                getVideosPagingUseCase()
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