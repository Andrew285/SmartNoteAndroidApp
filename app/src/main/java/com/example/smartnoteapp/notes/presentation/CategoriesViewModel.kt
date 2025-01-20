package com.example.smartnoteapp.notes.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.smartnoteapp.notes.domain.models.Category
import com.example.smartnoteapp.notes.domain.usecases.categories.GetCategoryByNameUseCase
import com.example.smartnoteapp.notes.utils.NoteConstants
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class CategoriesViewModel @Inject constructor(
    private val getCategoryByNameUseCase: GetCategoryByNameUseCase
): ViewModel() {

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
//            _status.value = OperationStatus.Error(e.message.toString())
            Log.d("TAGY", e.message.toString())
        }

        _categories.postValue(categories)
    }

    suspend fun getCategoryByName(name: String): Category? {
        return getCategoryByNameUseCase(name)
    }
}