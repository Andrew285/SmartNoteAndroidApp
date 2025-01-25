package com.example.smartnoteapp.auth.presentation.login

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
import com.example.smartnoteapp.core.presentation.states.LoginState
import com.example.smartnoteapp.core.utils.CustomToast
import com.example.smartnoteapp.databinding.FragmentLoginBinding
import com.example.smartnoteapp.main.presentation.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private val loginViewModel: LoginViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.Q)
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
                    loginViewModel.loginUser(email, password)
                }
                else {
                    CustomToast.makeText(requireContext(), resources.getString(R.string.fill_email_and_password), CustomToast.ToastType.ERROR)
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

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                loginViewModel.loginState.collect { state ->
                    when (state) {
                        is LoginState.Success<*> -> {
                            val intent = Intent(requireContext(), MainActivity::class.java)
                            requireActivity().startActivity(intent)
                            requireActivity().finish()
                        }
                        is LoginState.Error -> {
                            CustomToast.makeText(requireContext(), resources.getString(R.string.auth_failed), CustomToast.ToastType.ERROR)
                        }
                        else -> {}
                    }
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()

        loginViewModel.checkCurrentUser()

//        val currentUser = firebaseAuth.currentUser
//        if (currentUser != null) {
//            val intent = Intent(requireContext(), MainActivity::class.java)
//            requireActivity().startActivity(intent)
//            requireActivity().finish()
//        }
    }
}