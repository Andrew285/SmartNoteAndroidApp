package com.example.smartnoteapp.notes.presentation.home_notes

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
import com.example.smartnoteapp.notes.presentation.custom_views.NoteActionButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NotePagingAdapter(
    private val onItemClicked: OnItemViewClicked
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
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val currentNote = getItem(position)

        with (holder) {
            currentNote?.let {
                titleTextView.text = currentNote.title
                descriptionTextView.text = currentNote.description
                likeActionBtn.text = currentNote.likesCount.toString()
                commentActionBtn.text = currentNote.commentsCount.toString()

                currentNote.image.let { image ->
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