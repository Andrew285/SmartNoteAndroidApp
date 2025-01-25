package com.example.smartnoteapp.notes.presentation.posted_note

sealed class PostedNoteState {
    object Empty: PostedNoteState()
    object Loading: PostedNoteState()
    object Hidden: PostedNoteState()
    data class LoadedSucceed<T>(val data: T): PostedNoteState()
}