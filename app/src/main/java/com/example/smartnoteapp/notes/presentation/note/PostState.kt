package com.example.smartnoteapp.notes.presentation.note

sealed class PostState {
    object Loading: PostState()
    object Posted: PostState()
    data class Delayed(val message: String): PostState()
    data class Failed(val message: String): PostState()
}