package com.example.smartnoteapp.notes.presentation.home_notes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.smartnoteapp.databinding.FragmentHomeBinding
import com.example.smartnoteapp.notes.domain.models.Note
import com.example.smartnoteapp.core.presentation.BaseFragment
import com.example.smartnoteapp.core.presentation.OnItemViewClicked
import com.example.smartnoteapp.core.presentation.states.UiState
import com.example.smartnoteapp.notes.data.models.converters.Converters.mapToNote
import com.example.smartnoteapp.notes.data.models.remote.NoteRemote
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseFragment<PagingData<NoteRemote>>(), OnItemViewClicked {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val homeViewModel: HomeViewModel by viewModels()

    private lateinit var notesAdapter: NotePagingAdapter
    override lateinit var stateFlow: StateFlow<UiState<PagingData<NoteRemote>>>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        stateFlow = homeViewModel.state

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        swipeRefreshLayout = binding.homeSwipeRefreshLayout

        notesAdapter = NotePagingAdapter(this)

        with (binding) {
            notesRecyclerView.adapter = notesAdapter
            notesRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        }

        return binding.root
    }

    override fun handleLoading() {
        binding.progressBar.visibility = View.VISIBLE
        binding.notesRecyclerView.visibility = View.GONE
    }

    override fun handleSuccess(data: PagingData<NoteRemote>) {
        binding.progressBar.visibility = View.GONE
        binding.notesRecyclerView.visibility = View.VISIBLE

        lifecycleScope.launch {
            notesAdapter.submitData(data)
        }
    }

    override fun handleError(message: String) {
        binding.progressBar.visibility = View.GONE
        Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG).show()
    }

    override fun handleRefresh() {
        homeViewModel.getNotes()
    }

    override fun handleClick(position: Int) {
        lifecycleScope.launch {
            val note = notesAdapter.getItemAtPosition(position)?.mapToNote()
            val action = HomeFragmentDirections.actionHomeFragmentToPostedNoteFragment(note!!)
            findNavController().navigate(action)
        }
    }
}