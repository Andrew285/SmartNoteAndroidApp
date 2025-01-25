package com.example.smartnoteapp.notes.presentation.home_notes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.smartnoteapp.R
import com.example.smartnoteapp.core.utils.BitmapConverter
import com.example.smartnoteapp.notes.data.models.remote.NoteRemote
import com.example.smartnoteapp.notes.presentation.custom_views.NoteActionButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NotePagingAdapter: PagingDataAdapter<NoteRemote,NotePagingAdapter.NoteViewHolder>(NoteDiffCallback) {

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

        currentNote?.let {
            holder.titleTextView.text = currentNote.title
            holder.descriptionTextView.text = currentNote.description
            holder.likeActionBtn.text = currentNote.likesCount.toString()
            holder.commentActionBtn.text = currentNote.commentsCount.toString()

            val bitmap = BitmapConverter.converterStringToBitmap(currentNote.image)
            holder.photoImageView.setImageBitmap(bitmap)

            holder.likeActionBtn.let { btn ->
                btn.setOnClickListener {
                    CoroutineScope(Dispatchers.Main).launch {

                        var likesCountInt = btn.text.toInt()
                        likesCountInt += 1
                        btn.text = likesCountInt.toString()

//                        handleLikeAction(holder.likeActionBtn, currentNote)
                    }
                }
            }

            holder.commentActionBtn.setOnClickListener {
                TODO("Implement comment button listener")
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_note_layout, parent, false)
        return NoteViewHolder(view)
    }

//    private suspend fun handleLikeAction(likeButton: NoteActionButton, note: NoteRemote) {
//        // Prevent multiple rapid clicks
//        likeButton.isClickable = false
//
//        try {
//            // check if user liked this post
//            val usersLikedPost = note.likedUsers
//            if (usersLikedPost?.isNotEmpty() == true && usersLikedPost.contains())
//
//            // Toggle like state
//            val newLikeCount = if (note.isLiked) {
//                likeButton.setImage(R.drawable.heart)
//                note.likesCount - 1
//            } else {
//                likeButton.setImage(R.drawable.red_heart)
//                note.likesCount + 1
//            }
//
//            // Update local note object
//            note.isLiked = !note.isLiked
//            note.likesCount = newLikeCount
//
//            // Update button text
//            likeButton.text = newLikeCount.toString()
//
//            // Optional: Call listener for server-side update
//            onLikeClickListener?.invoke(note)
//
//        } catch (e: Exception) {
//            // Revert to previous state in case of error
//            updateLikeButtonState(likeButton, note)
//        } finally {
//            // Re-enable button
//            likeButton.isClickable = true
//        }
//    }
}