package com.example.smartnoteapp.auth.presentation.register

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.smartnoteapp.R
import com.example.smartnoteapp.databinding.FragmentRegisterBinding
import com.google.firebase.auth.FirebaseAuth

class RegisterFragment : Fragment() {
    private lateinit var binding: FragmentRegisterBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentRegisterBinding.inflate(layoutInflater)
        firebaseAuth = FirebaseAuth.getInstance()

        with (binding) {
            signupBtn.setOnClickListener {
                val email = emailEditText.text.toString()
                val password = passwordEditText.text.toString()

                if (email.isNotEmpty() && password.isNotEmpty()) {
                    firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                        if (it.isSuccessful) {
                            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
                        }
                        else {
                            Toast.makeText(requireContext(), it.exception?.message.toString(), Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                else {
                    Toast.makeText(requireContext(), "Please, fill email and password fields", Toast.LENGTH_SHORT).show()
                }

                loginTextView.setOnClickListener {
                    findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
                }

                // TODO("Implement GOOGLE Sign-in")
            }
        }


        return binding.root
    }
}