package com.example.smartnoteapp.notes.presentation.create_note

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.smartnoteapp.R
import com.example.smartnoteapp.core.utils.BitmapConverter
import com.example.smartnoteapp.databinding.FragmentCreateNoteBinding
import com.example.smartnoteapp.notes.domain.models.Category
import com.example.smartnoteapp.notes.domain.models.Note
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CreateNoteFragment : Fragment() {
    private lateinit var binding: FragmentCreateNoteBinding
    private val createNoteViewModel: CreateNoteViewModel by viewModels()

    private lateinit var activityResultLauncher: ActivityResultLauncher<Intent>
    private var selectedImageUri: Uri? = null
    private var bitmap: Bitmap? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentCreateNoteBinding.inflate(layoutInflater)

        lifecycleScope.launch {
            createNoteViewModel.categoriesViewModel.loadCategories()
        }

        createNoteViewModel.categoriesViewModel.categories.observe(viewLifecycleOwner) {
            addCategoriesAsChips(it, binding.categoriesChipGroup)
            binding.categoriesContainer.visibility = View.VISIBLE
        }

        with (binding) {
            addNoteBtn.setOnClickListener {
                if (areFieldsNotEmpty() && isAnyCategorySelected()) {
                    val title = titleEditText.text
                    val description = descriptionEditText.text
                    val categories = getSelectedCategories()
                    val bitmapString = BitmapConverter.converterBitmapToString(bitmap)
                    val note = Note(
                        title = title.toString(),
                        description = description.toString(),
                        timestamp = System.currentTimeMillis(),
                        image = bitmapString,
                        categories = categories
                    )

                    createNoteViewModel.notesViewModel.addNote(note)
                    Toast.makeText(requireContext(), "Successfully created!", Toast.LENGTH_SHORT).show()
                }
            }

            photoContainer.setOnClickListener {
                val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                activityResultLauncher.launch(intent)
            }
        }

        activityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                selectedImageUri = data?.data!!

                bitmap = BitmapConverter.uriToBitmap(requireContext(), selectedImageUri!!)!!
                binding.photoImageView.setImageBitmap(bitmap)
//                binding.photoImageView.setImageURI(selectedImageUri)
                binding.photoContainer.setBackgroundResource(R.color.transparent)
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
        return createNoteViewModel.categoryNameIdMap.isNotEmpty()
    }
    private fun getSelectedCategories(): List<Category> {
        return createNoteViewModel.categoryNameIdMap.map { it ->
            Category(
                it.value,
                it.key
            )
        }
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

        chip.setOnCheckedChangeListener() { buttonView, isChecked ->
            createNoteViewModel.checkCategory(buttonView.text.toString(), isChecked)
        }
        chipGroup.addView(chip)
    }
}