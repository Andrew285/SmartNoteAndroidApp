package com.example.smartnoteapp.notes.presentation.my_notes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartnoteapp.notes.domain.models.Note
import com.example.smartnoteapp.notes.domain.usecases.local_notes.GetAllNotesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyNotesViewModel @Inject constructor(
    private val getAllNotesUseCase: GetAllNotesUseCase
): ViewModel() {
    private var _notes = MutableLiveData<List<Note>>()
    val notes: LiveData<List<Note>> = _notes

    fun loadNotes() {
        viewModelScope.launch(Dispatchers.IO) {
            val notes = getAllNotesUseCase()
            _notes.postValue(notes)
        }
    }
}