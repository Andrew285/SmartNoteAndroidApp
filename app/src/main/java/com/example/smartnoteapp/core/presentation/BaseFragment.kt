package com.example.smartnoteapp.core.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.smartnoteapp.core.presentation.states.UiState
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

abstract class BaseFragment<T>: Fragment() {

    abstract val stateFlow: StateFlow<UiState<T>>
    protected var swipeRefreshLayout: SwipeRefreshLayout? = null

    abstract fun handleLoading()
    abstract fun handleSuccess(data: T)
    abstract fun handleError(message: String)
    abstract fun handleRefresh()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setObservables()
        setRefreshLayoutListener()
    }

    private fun setObservables() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                stateFlow.collect { state ->
                    when (state) {
                        is UiState.Loading -> {
                            setRefreshing(true)
                            handleLoading()
                        }
                        is UiState.Success -> {
                            setRefreshing(false)
                            handleSuccess(state.data)
                        }
                        is UiState.Error -> {
                            setRefreshing(false)
                            handleError(state.message)
                        }
                        else -> {}
                    }
                }
            }
        }
    }

    private fun setRefreshLayoutListener() {
        swipeRefreshLayout?.setOnRefreshListener {
            handleRefresh()
        }
    }

    protected fun setRefreshing(isRefreshing: Boolean) {
        swipeRefreshLayout?.isRefreshing = isRefreshing
    }
}