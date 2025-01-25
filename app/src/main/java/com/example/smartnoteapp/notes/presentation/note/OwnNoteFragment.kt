package com.example.smartnoteapp.notes.presentation.note

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.smartnoteapp.R
import com.example.smartnoteapp.core.utils.BitmapConverter
import com.example.smartnoteapp.core.utils.CustomToast
import com.example.smartnoteapp.databinding.FragmentOwnNoteBinding
import com.example.smartnoteapp.notes.domain.models.Note
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class OwnNoteFragment : Fragment() {
    private lateinit var binding: FragmentOwnNoteBinding
    private val fragmentArgs: OwnNoteFragmentArgs by navArgs()
    private val ownNotesViewModel: OwnNoteViewModel by viewModels()
    private var currentNote: Note? = null

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOwnNoteBinding.inflate(layoutInflater)

        fetchData()
        setClickListeners()
        setObserveData()

        return binding.root
    }

    private fun fetchData() {
        with (binding) {
            currentNote = fragmentArgs.note
            currentNote?.let { it ->
                titleTextView.text = it.title
                descriptionTextView.text = it.description

                val imageBitmap = BitmapConverter.converterStringToBitmap(it.image)
                photoImageView.setImageBitmap(imageBitmap)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun setClickListeners() {
        with (binding) {
            postBtn.setOnClickListener {
                val delayDialog = PostNoteDelayDialog()
                delayDialog.onDelaySelected = { timeToDelay ->
                    ownNotesViewModel.postNote(currentNote!!, timeToDelay)
                }
                delayDialog.show(parentFragmentManager, "DelayDialog")
            }

            deleteBtn.setOnClickListener {
                ownNotesViewModel.deleteNote(currentNote?.id!!)
                CustomToast.makeText(requireContext(), getString(R.string.note_deleted), CustomToast.ToastType.SUCCESS)
                findNavController().navigate(R.id.action_ownNoteFragment_to_myNotesFragment)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun setObserveData() {
        lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                ownNotesViewModel.notePostState.collect { postState ->
                    when (postState) {
                        is PostState.Posted -> {
                            CustomToast.makeText(requireContext(), getString(R.string.note_posted_successful), CustomToast.ToastType.SUCCESS)
                        }
                        is PostState.Delayed -> {
                            CustomToast.makeText(requireContext(), postState.message, CustomToast.ToastType.INFO)
                        }
                        is PostState.Failed -> {
                            CustomToast.makeText(requireContext(), postState.message, CustomToast.ToastType.ERROR)
                        }
                        is PostState.Loading -> { }
                    }
                }
            }
        }
    }
}