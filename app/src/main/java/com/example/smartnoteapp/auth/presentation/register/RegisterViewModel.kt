package com.example.smartnoteapp.auth.presentation.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartnoteapp.auth.domain.usecases.SignUpUseCase
import com.example.smartnoteapp.core.presentation.states.SignUpState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val signUpUseCase: SignUpUseCase,
): ViewModel() {
    private val _signUpState = MutableStateFlow<SignUpState>(SignUpState.Empty())
    val signUpState: StateFlow<SignUpState> = _signUpState

    fun signUpUser(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            signUpUseCase(email, password) { result ->
                result.onSuccess { user ->
                    _signUpState.value = SignUpState.Success(user)
                }.onFailure { e ->
                    _signUpState.value = SignUpState.Error(e.message.toString())
                }
            }
        }
    }
}