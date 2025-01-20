package com.example.smartnoteapp.notes.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartnoteapp.core.presentation.OperationStatus
import com.example.smartnoteapp.notes.domain.models.Note
import com.example.smartnoteapp.notes.domain.usecases.notes.AddNoteUseCase
import com.example.smartnoteapp.notes.domain.usecases.notes.ClearDatabaseUseCase
import com.example.smartnoteapp.notes.domain.usecases.notes.DeleteNoteUseCase
import com.example.smartnoteapp.notes.domain.usecases.notes.GetAllNotesUseCase
import com.example.smartnoteapp.notes.domain.usecases.notes.PostNoteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class NotesViewModel @Inject constructor(
    private val addNoteUseCase: AddNoteUseCase,
    private val getAllNotesUseCase: GetAllNotesUseCase,
    private val deleteNoteUseCase: DeleteNoteUseCase,
    private val clearDatabaseUseCase: ClearDatabaseUseCase,
    private val postNoteUseCase: PostNoteUseCase
) : ViewModel() {
    private var _notes = MutableLiveData<List<Note>>()
    val notes: LiveData<List<Note>> = _notes

    private var _status = MutableStateFlow<OperationStatus>(OperationStatus.Loading)
    val status: StateFlow<OperationStatus> = _status

    fun loadNotes() {
        viewModelScope.launch(Dispatchers.IO) {
            val notes = getAllNotesUseCase()
            _notes.postValue(notes)
        }
    }

    fun addNote(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            addNoteUseCase(note)
            loadNotes()
        }
    }

//    fun deleteAllNotes() {
//        viewModelScope.launch(Dispatchers.IO) {
//
//        }
//    }

    fun clearDatabase() {
        viewModelScope.launch(Dispatchers.IO) {
            clearDatabaseUseCase()
        }
    }

//    fun editNote(note: Note) {
//        viewModelScope.launch {
//            noteRepository.editNote(note)
//            loadNotes()
//        }
//    }
//

    fun deleteNote(noteId: Long) {
        viewModelScope.launch {
            deleteNoteUseCase(noteId)
            loadNotes()
        }
    }

    fun postNote(note: Note) {
        viewModelScope.launch {
            postNoteUseCase(note)
        }
    }
}