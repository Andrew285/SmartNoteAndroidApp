package com.example.smartnoteapp.profile.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.smartnoteapp.R
import com.example.smartnoteapp.databinding.FragmentProfileBinding
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding

    @Inject
    lateinit var firebaseAuth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentProfileBinding.inflate(layoutInflater)

        with (binding) {
            val currentUser = firebaseAuth.currentUser
            if (currentUser != null) {
                emailTextView.text = currentUser.email
                phoneTextView.text = currentUser.phoneNumber
            }

            activityContainer.setOnClickListener {
                findNavController().navigate(R.id.action_profileFragment_to_activityFragment)
            }

            notesContainer.setOnClickListener {
                findNavController().navigate(R.id.action_profileFragment_to_myNotesFragment)
            }

            settingsContainer.setOnClickListener {
                findNavController().navigate(R.id.action_profileFragment_to_settingsFragment)
            }
        }

        return binding.root
    }
}