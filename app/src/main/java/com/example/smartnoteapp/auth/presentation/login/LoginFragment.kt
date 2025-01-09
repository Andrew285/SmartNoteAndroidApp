package com.example.smartnoteapp.auth.presentation.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.smartnoteapp.R
import com.example.smartnoteapp.databinding.FragmentLoginBinding
import com.example.smartnoteapp.databinding.FragmentRegisterBinding
import com.example.smartnoteapp.main.presentation.MainActivity
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding

    @Inject
    lateinit var firebaseAuth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentLoginBinding.inflate(layoutInflater)

        with (binding) {
            loginBtn.setOnClickListener {
                val email = emailEditText.text.toString()
                val password = passwordEditText.text.toString()

                if (email.isNotEmpty() && password.isNotEmpty()) {
                    firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                        if (it.isSuccessful) {
                            val intent = Intent(requireContext(), MainActivity::class.java)
                            requireActivity().startActivity(intent)
                            requireActivity().finish()
                        }
                        else {
                            Toast.makeText(requireContext(), "Firebase authentication failed", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                else {
                    Toast.makeText(requireContext(), "Please, fill email and password fields", Toast.LENGTH_SHORT).show()
                }
            }

            signupTextView.setOnClickListener {
                findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
            }

            forgotPasswordTextView.setOnClickListener {
                // TODO("Implement this textView Click")
            }

            // TODO("Implement GOOGLE Sign-in")
        }

        return binding.root
    }

    override fun onStart() {
        super.onStart()

        val currentUser = firebaseAuth.currentUser
        if (currentUser != null) {
            val intent = Intent(requireContext(), MainActivity::class.java)
            requireActivity().startActivity(intent)
            requireActivity().finish()
        }
    }
}