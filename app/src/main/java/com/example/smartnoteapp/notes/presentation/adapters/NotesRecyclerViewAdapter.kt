package com.example.smartnoteapp.notes.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.smartnoteapp.R
import com.example.smartnoteapp.core.presentation.OnItemViewClicked
import com.example.smartnoteapp.core.utils.BitmapConverter
import com.example.smartnoteapp.notes.domain.models.Note

class NotesRecyclerViewAdapter(
    private var notes: List<Note>,
    private val onItemClicked: OnItemViewClicked
): RecyclerView.Adapter<NotesRecyclerViewAdapter.NoteViewHolder>() {

    class NoteViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        val descriptionTextView: TextView = itemView.findViewById(R.id.descriptionTextView)
        val photoImageView: ImageView = itemView.findViewById(R.id.photoImageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_my_note_layout, parent, false)
        return NoteViewHolder(view)
    }

    override fun getItemCount(): Int {
        return notes.size
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val currentNote = notes[position]

        holder.titleTextView.text = currentNote.title
        holder.descriptionTextView.text = currentNote.description

        val imageBitmap = BitmapConverter.converterStringToBitmap(currentNote.image)
        holder.photoImageView.setImageBitmap(imageBitmap)

        holder.itemView.setOnClickListener {
            onItemClicked.handleClick(position)
        }
    }

    fun updateData(newNoteList: List<Note>) {
        notes = newNoteList
        notifyDataSetChanged()
    }
}