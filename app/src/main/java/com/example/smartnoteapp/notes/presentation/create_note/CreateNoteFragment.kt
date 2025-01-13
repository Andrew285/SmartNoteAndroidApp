package com.example.smartnoteapp.notes.presentation.create_note

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.view.children
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.smartnoteapp.databinding.FragmentCreateNoteBinding
import com.example.smartnoteapp.notes.domain.models.Category
import com.example.smartnoteapp.notes.domain.models.Note
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.firebase.Timestamp
import kotlinx.coroutines.launch

class CreateNoteFragment : Fragment() {
    private lateinit var binding: FragmentCreateNoteBinding
    private val notesViewModel: NotesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentCreateNoteBinding.inflate(layoutInflater)

        lifecycleScope.launch {
            notesViewModel.loadCategories()
        }

        notesViewModel.categories.observe(viewLifecycleOwner) {
            addCategoriesAsChips(it, binding.categoriesChipGroup)
            binding.categoriesContainer.visibility = View.VISIBLE
        }

        with (binding) {
            addNoteBtn.setOnClickListener {
                if (areFieldsNotEmpty() && isAnyCategorySelected()) {
                    val title = titleEditText.text
                    val description = descriptionEditText.text
                    val categories = getSelectedCategories()

                    val note = Note(
                        title = title.toString(),
                        description = description.toString(),
                        timestamp = System.currentTimeMillis(),
                        categoryIds = categories.map { it.id }
                    )
                }
            }
        }


        return binding.root
    }

    private fun areFieldsNotEmpty(vararg editTexts: EditText): Boolean {
        for (editText in editTexts) {
            if (editText.text.isEmpty()) return false
        }
        return true
    }

    private fun isAnyCategorySelected(): Boolean {
        return getSelectedCategoryChips().isNotEmpty()
    }

    private fun getSelectedCategories(): List<Category> {
        val selectedChips = getSelectedCategoryChips()
        return selectedChips.map {
            Category(
                name = it.text.toString(),
                isSelected = true
            )
        }
    }

    private fun getSelectedCategoryChips(): List<Chip> {
        val chips = binding.categoriesChipGroup.children
        return chips.filter {
            it is Chip && it.isChecked
        }.map {
            it as Chip
        }.toList()
    }

    private fun addCategoriesAsChips(categories: List<Category>, chipGroup: ChipGroup) {
        for (category in categories) {
            addCategoryAsChip(category, chipGroup)
        }
    }

    private fun addCategoryAsChip(category: Category, chipGroup: ChipGroup) {
        val chip = Chip(requireContext()).apply {
            this.text = category.name
            isCheckable = true
        }
        chipGroup.addView(chip)
    }
}