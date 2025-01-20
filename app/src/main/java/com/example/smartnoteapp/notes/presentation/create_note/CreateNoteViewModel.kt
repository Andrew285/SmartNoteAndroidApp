package com.example.smartnoteapp.notes.presentation.create_note

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartnoteapp.notes.presentation.CategoriesViewModel
import com.example.smartnoteapp.notes.presentation.NotesViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateNoteViewModel @Inject constructor(
    val notesViewModel: NotesViewModel,
    val categoriesViewModel: CategoriesViewModel
): ViewModel() {

    val categoryNameIdMap: MutableMap<String, Long> = mutableMapOf()

    fun checkCategory(categoryName: String, isChecked: Boolean) {
        viewModelScope.launch {
            if (isChecked) {
                val category = categoriesViewModel.getCategoryByName(categoryName)
                if (category != null) {
                    categoryNameIdMap[categoryName] = category.id
                }
            }
            else {
                categoryNameIdMap.remove(categoryName)
            }
        }
    }
}