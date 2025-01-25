package com.example.smartnoteapp.profile.presentation

import android.content.Intent
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
import com.example.smartnoteapp.R
import com.example.smartnoteapp.auth.domain.models.User
import com.example.smartnoteapp.auth.presentation.AuthActivity
import com.example.smartnoteapp.core.presentation.states.UiState
import com.example.smartnoteapp.core.utils.CustomToast
import com.example.smartnoteapp.databinding.FragmentProfileBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private val profileViewModel: ProfileViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentProfileBinding.inflate(layoutInflater)

        setClickListeners()
        setObserveUserState()

        profileViewModel.loadUserData()

        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun setObserveUserState() {
        lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                profileViewModel.userState.collect { userState ->
                    when (userState) {
                        is UiState.Success -> {
                            setUserData(userState.data)
                        }
                        is UiState.Error -> {
                            CustomToast.makeText(requireContext(), userState.message, CustomToast.ToastType.ERROR)
                        }
                        else -> { }
                    }
                }
            }
        }
    }

    private fun setClickListeners() {
        with (binding) {
            activityContainer.setOnClickListener {
                findNavController().navigate(R.id.action_profileFragment_to_activityFragment)
            }

            notesContainer.setOnClickListener {
                findNavController().navigate(R.id.action_profileFragment_to_myNotesFragment)
            }

            settingsContainer.setOnClickListener {
                findNavController().navigate(R.id.action_profileFragment_to_settingsFragment)
            }

            logoutContainer.setOnClickListener {
                profileViewModel.signOutUser()
                CustomToast.makeText(requireContext(), resources.getString(R.string.sign_out_successfully), CustomToast.ToastType.ERROR)
                requireActivity().finish()
                val intent = Intent(requireContext(), AuthActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun setUserData(user: User) {
        with (binding) {
            nameTextView.text = resources.getString(R.string.full_name_placeholders, user.name, user.surname)
            ageTextView.text = resources.getString(R.string.user_age_placeholders, user.age)
            nickNameTextView.text = user.nickname
            statusTextView.text = user.status
        }
    }
}