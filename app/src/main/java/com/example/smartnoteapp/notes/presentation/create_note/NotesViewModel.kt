package com.example.smartnoteapp.notes.presentation.create_note

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.smartnoteapp.core.presentation.OperationStatus
import com.example.smartnoteapp.core.utils.CustomToast
import com.example.smartnoteapp.notes.domain.models.Category
import com.example.smartnoteapp.notes.domain.models.Note
import com.example.smartnoteapp.notes.utils.NoteConstants
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.tasks.await

class NotesViewModel: ViewModel() {
    private var _categories = MutableLiveData<List<Category>>()
    val categories: LiveData<List<Category>> = _categories

    private var _status = MutableStateFlow<OperationStatus>(OperationStatus.Loading)
    val status: StateFlow<OperationStatus> = _status

    suspend fun loadCategories() {
        val db = Firebase.firestore

        val categories: MutableList<Category> = mutableListOf()
        try {
            val ref = db.collection(NoteConstants.CATEGORIES_COLLECTION)
            val result = ref.get().await()
            if (!result.isEmpty) {
                for (document in result.documents) {
                    val category = document.toObject<Category>()
                    category?.let {
                        categories.add(it)
                    }
                }
            }
        } catch (e: Exception) {
            _status.value = OperationStatus.Error(e.message.toString())
        }

        _categories.postValue(categories)
    }

    fun addNote(note: Note) {

    }
}