package com.example.smartnoteapp.notes.presentation.home_notes

import android.content.res.ColorStateList
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.smartnoteapp.R
import com.example.smartnoteapp.core.presentation.OnItemViewClicked
import com.example.smartnoteapp.core.utils.BitmapConverter
import com.example.smartnoteapp.notes.data.models.remote.NoteRemote
import com.example.smartnoteapp.notes.domain.usecases.categories.GetCategoryByNameUseCase
import com.example.smartnoteapp.notes.presentation.custom_views.NoteActionButton
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class NotePagingAdapter @Inject constructor(
    private val onItemClicked: OnItemViewClicked,
    private val viewModel: HomeViewModel
): PagingDataAdapter<NoteRemote,NotePagingAdapter.NoteViewHolder>(NoteDiffCallback) {

    object NoteDiffCallback : DiffUtil.ItemCallback<NoteRemote>() {
        override fun areItemsTheSame(oldItem: NoteRemote, newItem: NoteRemote) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: NoteRemote, newItem: NoteRemote) = oldItem == newItem
    }

    class NoteViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        val descriptionTextView: TextView = itemView.findViewById(R.id.descriptionTextView)
        val photoImageView: ImageView = itemView.findViewById(R.id.photoImageView)
        val likeActionBtn: NoteActionButton = itemView.findViewById(R.id.likeActionBtn)
        val commentActionBtn: NoteActionButton = itemView.findViewById(R.id.commentActionBtn)
        val categoriesChipGroup: ChipGroup = itemView.findViewById(R.id.categoriesChipGroup)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val currentNote = getItem(position)

        with (holder) {
            currentNote?.let { it ->
                titleTextView.text = it.title
                descriptionTextView.text = it.description
                likeActionBtn.text = it.likesCount.toString()
                commentActionBtn.text = it.commentsCount.toString()

                categoriesChipGroup.removeAllViews()

                it.categories?.forEach { categoryName ->
                    CoroutineScope(Dispatchers.Main).launch {
                        viewModel.getCategoryByName(categoryName) { it ->
                            val chip = Chip(itemView.context).apply {
                                text = it.name
                                isCloseIconVisible = false
                                isClickable = true
                                isCheckable = false
                                chipBackgroundColor = ColorStateList.valueOf(Color.parseColor(it.color.toString())) // Apply color
                            }
                            categoriesChipGroup.addView(chip)
                        }
                    }
                }

                it.image.let { image ->
                    if (image.contains("http")) {
                        Glide.with(itemView.context)
                            .load(image)
                            .into(photoImageView)
                    }
                    else {
                        val bitmap = BitmapConverter.converterStringToBitmap(image)
                        photoImageView.setImageBitmap(bitmap)
                    }
                }

                likeActionBtn.let { btn ->
                    btn.setOnClickListener {
                        CoroutineScope(Dispatchers.Main).launch {

                            var likesCountInt = btn.text.toInt()
                            likesCountInt += 1
                            btn.text = likesCountInt.toString()

                            TODO("Finish implementing like button listener")
                        }
                    }
                }

                commentActionBtn.setOnClickListener {
                    TODO("Implement comment button listener")
                }

                itemView.setOnClickListener {
                    onItemClicked.handleClick(position)
                }
            }
        }
    }

    fun getItemAtPosition(position: Int): NoteRemote? {
        return getItem(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_note_layout, parent, false)
        return NoteViewHolder(view)
    }
}