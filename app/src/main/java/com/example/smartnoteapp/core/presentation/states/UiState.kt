package com.example.smartnoteapp.core.presentation.states

sealed class UiState<T> {
    class Empty<T> : UiState<T>()
    class Loading<T> : UiState<T>()
    data class Success<T>(val data: T) : UiState<T>()
    data class Error<T>(val message: String) : UiState<T>()
}