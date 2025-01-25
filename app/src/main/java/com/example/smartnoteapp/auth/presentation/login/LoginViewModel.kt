package com.example.smartnoteapp.auth.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartnoteapp.auth.domain.usecases.GetCurrentUserUseCase
import com.example.smartnoteapp.auth.domain.usecases.LoginUseCase
import com.example.smartnoteapp.core.presentation.states.LoginState
import com.example.smartnoteapp.core.presentation.states.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val getCurrentUserUseCase: GetCurrentUserUseCase
): ViewModel() {
    private val _loginState = MutableStateFlow<LoginState>(LoginState.Empty())
    val loginState: StateFlow<LoginState> = _loginState

    fun loginUser(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            loginUseCase(email, password) { result ->
                result.onSuccess { user ->
                    _loginState.value = LoginState.Success(user)
                }.onFailure { e ->
                    _loginState.value = LoginState.Error(e.message.toString())
                }
            }
        }
    }

    fun checkCurrentUser() {
        viewModelScope.launch(Dispatchers.IO) {
            val currentUser = getCurrentUserUseCase()
            if (currentUser != null) {
                _loginState.value = LoginState.Success(currentUser)
            }
        }
    }
}