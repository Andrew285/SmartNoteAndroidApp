package com.example.smartnoteapp.notes.presentation.my_notes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.smartnoteapp.core.presentation.OnItemViewClicked
import com.example.smartnoteapp.databinding.FragmentMyNotesBinding
import com.example.smartnoteapp.notes.domain.models.Note
import com.example.smartnoteapp.notes.presentation.adapters.NotesRecyclerViewAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyNotesFragment : Fragment(), OnItemViewClicked {
    private lateinit var binding: FragmentMyNotesBinding
    private val myNotesViewModel: MyNotesViewModel by viewModels()
    private lateinit var notesAdapter: NotesRecyclerViewAdapter

    private var noteList: List<Note> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentMyNotesBinding.inflate(layoutInflater)

        notesAdapter = NotesRecyclerViewAdapter(noteList, this)
        binding.notesRecylerView.layoutManager = LinearLayoutManager(requireContext())
        binding.notesRecylerView.adapter = notesAdapter

        myNotesViewModel.notesViewModel.notes.observe(viewLifecycleOwner) { it ->
            noteList = it
            notesAdapter.updateData(it)
        }

        loadData()

        return binding.root
    }

    private fun loadData() {
        myNotesViewModel.notesViewModel.loadNotes()
        notesAdapter.notifyDataSetChanged()
    }

    override fun handleClick(position: Int) {
        val noteToPass = noteList[position]
        val action = MyNotesFragmentDirections.actionMyNotesFragmentToOwnNoteFragment(noteToPass)
        findNavController().navigate(action)
    }
}