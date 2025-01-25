package com.example.smartnoteapp.auth.presentation.register

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.smartnoteapp.R
import com.example.smartnoteapp.core.presentation.states.SignUpState
import com.example.smartnoteapp.core.utils.CustomToast
import com.example.smartnoteapp.databinding.FragmentRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RegisterFragment : Fragment() {
    private lateinit var binding: FragmentRegisterBinding
    private val registerViewModel: RegisterViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentRegisterBinding.inflate(layoutInflater)

        with (binding) {
            signupBtn.setOnClickListener {
                val email = emailEditText.text.toString()
                val password = passwordEditText.text.toString()

                if (email.isNotEmpty() && password.isNotEmpty()) {
                    registerViewModel.signUpUser(email, password)

//                    firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
//                        if (it.isSuccessful) {
//                            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
//                        }
//                        else {
//                            Toast.makeText(requireContext(), it.exception?.message.toString(), Toast.LENGTH_SHORT).show()
//                        }
//                    }
                }
                else {
                    CustomToast.makeText(requireContext(), resources.getString(R.string.fill_email_and_password), CustomToast.ToastType.ERROR)

//                    Toast.makeText(requireContext(), "Please, fill email and password fields", Toast.LENGTH_SHORT).show()
                }

                loginTextView.setOnClickListener {
                    findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
                }

                // TODO("Implement GOOGLE Sign-in")
            }
        }


        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                registerViewModel.signUpState.collect { state ->
                    when (state) {
                        is SignUpState.Success<*> -> {
                            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
                        }
                        is SignUpState.Error -> {
                            CustomToast.makeText(requireContext(), resources.getString(R.string.signup_error), CustomToast.ToastType.ERROR)
                        }
                        else -> {}
                    }
                }
            }
        }
    }
}