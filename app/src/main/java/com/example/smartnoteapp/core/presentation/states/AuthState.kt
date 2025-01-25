package com.example.smartnoteapp.core.presentation.states

sealed interface AuthState {
    data class Success(val userId: String? = null) : AuthState
    data class Error(val message: String) : AuthState
}

sealed class LoginState : AuthState {
    class Empty : LoginState()
    data class Success<T>(val data: T? = null) : LoginState()
    data class Error(val message: String) : LoginState()
}

sealed class SignUpState : AuthState {
    class Empty : SignUpState()
    data class Success<T>(val data: T? = null) : SignUpState()
    data class Error(val message: String) : SignUpState()
}