package com.example.smartnoteapp.core.presentation

import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import com.example.smartnoteapp.core.utils.CustomToast

class BaseFragment: Fragment() {

    fun observeStatus(status: LiveData<OperationStatus>) {
        status.observe(viewLifecycleOwner) { status ->
            when (status) {
                is OperationStatus.Success -> showStatus(status.message, CustomToast.ToastType.SUCCESS)
                is OperationStatus.Error -> showStatus(status.message, CustomToast.ToastType.ERROR)
                is OperationStatus.Info -> showStatus(status.message, CustomToast.ToastType.INFO)
                OperationStatus.Loading -> showStatus("", CustomToast.ToastType.INFO)
            }
        }
    }

    private fun showStatus(message: String, type: CustomToast.ToastType) {
        CustomToast.makeText(requireContext(), message, type)
    }
}