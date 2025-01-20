package com.example.smartnoteapp.notes.presentation.note

import androidx.lifecycle.ViewModel
import com.example.smartnoteapp.notes.presentation.NotesViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OwnNoteViewModel @Inject constructor(
    val notesViewModel: NotesViewModel
): ViewModel() {

}