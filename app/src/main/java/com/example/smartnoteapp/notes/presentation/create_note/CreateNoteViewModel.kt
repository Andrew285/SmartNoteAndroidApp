package com.example.smartnoteapp.notes.presentation.create_note

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartnoteapp.notes.domain.models.Category
import com.example.smartnoteapp.notes.domain.models.Note
import com.example.smartnoteapp.notes.domain.usecases.categories.GetCategoryByNameUseCase
import com.example.smartnoteapp.notes.domain.usecases.local_notes.AddNoteUseCase
import com.example.smartnoteapp.notes.utils.NoteConstants
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class CreateNoteViewModel @Inject constructor(
    private val getCategoryByNameUseCase: GetCategoryByNameUseCase,
    private val addNoteUseCase: AddNoteUseCase
): ViewModel() {

    val categoryNameIdMap: MutableMap<String, Long> = mutableMapOf()

    private val _categories = MutableLiveData<List<Category>>()
    val categories: LiveData<List<Category>> = _categories

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
            Log.d("TAGY", e.message.toString())
        }

        _categories.postValue(categories)
    }

    fun checkCategory(categoryName: String, isChecked: Boolean) {
        viewModelScope.launch {
            if (isChecked) {
                val category = getCategoryByNameUseCase(categoryName)
                if (category != null) {
                    categoryNameIdMap[categoryName] = category.id
                }
            }
            else {
                categoryNameIdMap.remove(categoryName)
            }
        }
    }

    fun addNote(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            addNoteUseCase(note)
        }
    }
}