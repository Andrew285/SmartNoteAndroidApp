package com.example.smartnoteapp.notes.presentation.my_notes

import androidx.lifecycle.ViewModel
import com.example.smartnoteapp.notes.presentation.NotesViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MyNotesViewModel @Inject constructor(
    val notesViewModel: NotesViewModel
): ViewModel() {

}