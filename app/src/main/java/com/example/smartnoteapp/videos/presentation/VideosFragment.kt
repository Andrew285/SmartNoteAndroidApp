package com.example.smartnoteapp.videos.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.smartnoteapp.core.presentation.BaseFragment
import com.example.smartnoteapp.core.presentation.states.UiState
import com.example.smartnoteapp.databinding.FragmentVideosBinding
import com.example.smartnoteapp.videos.domain.models.Video
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@AndroidEntryPoint
class VideosFragment : BaseFragment<PagingData<Video>>() {
    private var _binding: FragmentVideosBinding? = null
    private val binding get() = _binding!!
    private val videoViewModel: VideoViewModel by viewModels()

    private lateinit var videoAdapter: VideoPagingAdapter
    override lateinit var stateFlow: StateFlow<UiState<PagingData<Video>>>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        stateFlow = videoViewModel.state

        _binding = FragmentVideosBinding.inflate(inflater, container, false)
        swipeRefreshLayout = binding.homeSwipeRefreshLayout

        videoAdapter = VideoPagingAdapter()

        with (binding) {
            videosRecyclerView.adapter = videoAdapter
            videosRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        }

        return binding.root
    }

    override fun handleLoading() {
        binding.progressBar.visibility = View.VISIBLE
        binding.videosRecyclerView.visibility = View.GONE
    }

    override fun handleSuccess(data: PagingData<Video>) {
        binding.progressBar.visibility = View.GONE
        binding.videosRecyclerView.visibility = View.VISIBLE

        lifecycleScope.launch {
            videoAdapter.submitData(data)
        }
    }

    override fun handleError(message: String) {
        binding.progressBar.visibility = View.GONE
        Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG).show()
    }

    override fun handleRefresh() {
        videoViewModel.getVideos()
    }
}