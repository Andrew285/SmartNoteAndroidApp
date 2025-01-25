package com.example.smartnoteapp.profile.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartnoteapp.auth.domain.models.User
import com.example.smartnoteapp.auth.domain.usecases.GetUserInfoUseCase
import com.example.smartnoteapp.auth.domain.usecases.SignOutUseCase
import com.example.smartnoteapp.core.presentation.states.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val signOutUseCase: SignOutUseCase,
    private val getUserInfoUseCase: GetUserInfoUseCase
): ViewModel() {

    private val _userState = MutableStateFlow<UiState<User>>(UiState.Empty())
    val userState: StateFlow<UiState<User>> = _userState

    fun updateUserState(newState: UiState<User>) {
        _userState.value = newState
    }

    fun loadUserData() {
        viewModelScope.launch {
            updateUserState(UiState.Loading())

            val result = getUserInfoUseCase()
            result.onSuccess { user ->
                user?.let {
                    updateUserState(UiState.Success(user))
                }
            }.onFailure {
                updateUserState(UiState.Error("Error retrieving user's data"))
            }
        }
    }

    fun signOutUser() {
        viewModelScope.launch {
            signOutUseCase()
        }
    }
}