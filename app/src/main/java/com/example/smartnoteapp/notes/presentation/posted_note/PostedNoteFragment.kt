package com.example.smartnoteapp.notes.presentation.posted_note

import android.app.AlertDialog
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
import com.bumptech.glide.Glide
import com.example.smartnoteapp.R
import com.example.smartnoteapp.core.utils.BitmapConverter
import com.example.smartnoteapp.core.utils.CustomToast
import com.example.smartnoteapp.databinding.FragmentPostedNoteBinding
import com.example.smartnoteapp.notes.domain.models.Note
import com.example.smartnoteapp.notes.presentation.note.PostState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PostedNoteFragment : Fragment() {
    private lateinit var binding: FragmentPostedNoteBinding
    private val fragmentArgs: PostedNoteFragmentArgs by navArgs()
    private val postedNoteViewModel: PostedNoteViewModel by viewModels()
    private var postedNote: Note? = null

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentPostedNoteBinding.inflate(layoutInflater)

        setClickListeners()
        setObservers()

        loadPost()

        return binding.root
    }

    private fun loadPost() {
        postedNote = fragmentArgs.note

        with (binding) {
            postedNote?.let { it ->
                titleTextView.text = it.title
                descriptionTextView.text = it.description

                it.image.let { image ->
                    if (image.contains("http")) {
                        Glide.with(requireContext())
                            .load(image)
                            .into(photoImageView)
                    }
                    else {
                        val bitmap = BitmapConverter.converterStringToBitmap(image)
                        photoImageView.setImageBitmap(bitmap)
                    }
                }

                likesCountTextView.text = resources.getString(R.string.liked_count, it.likesCount)
                commentsCountTextView.text = resources.getString(R.string.comments_count, it.commentsCount)
            }
        }
    }

    private fun setClickListeners() {
        binding.hideBtn.setOnClickListener {
            showAlertDialog {
                postedNoteViewModel.hidePost(postedNote?.id!!.toInt())

                findNavController().navigate(R.id.action_postedNoteFragment_to_homeFragment)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun setObservers() {
        lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                postedNoteViewModel.postedNoteState.collect { state ->
                    when (state) {
                        is PostedNoteState.Hidden -> {
                            setProgressBarVisible(false)
                            CustomToast.makeText(requireContext(), resources.getString(R.string.note_is_hidden), CustomToast.ToastType.INFO)
                        }
                        is PostedNoteState.Loading -> {
                            setProgressBarVisible(true)
                        }
                        else -> {
                            setProgressBarVisible(false)
                        }
                    }
                }
            }
        }
    }

    private fun showAlertDialog(onPositiveButtonResult: () -> Unit) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setMessage(resources.getString(R.string.do_you_want_to_hide_note))
        builder.setTitle(resources.getString(R.string.warning))
        builder.setCancelable(false)
        builder.setPositiveButton(resources.getString(R.string.yes)) {
                dialog, which -> onPositiveButtonResult()
        }

        builder.setNegativeButton(resources.getString(R.string.no)) {
                dialog, which -> dialog.cancel()
        }

        val alertDialog = builder.create()
        alertDialog.show()
    }

    private fun setProgressBarVisible(isVisible: Boolean) {
        binding.progressBar.visibility = if (isVisible) View.VISIBLE else View.GONE
    }
}