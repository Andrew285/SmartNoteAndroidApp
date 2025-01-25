package com.example.smartnoteapp.auth.domain.usecases

import com.example.smartnoteapp.auth.domain.repository.IUserRepository
import com.google.firebase.auth.FirebaseUser
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val userRepository: IUserRepository
) {
    suspend operator fun invoke(email: String, password: String, onResult: (Result<FirebaseUser?>) -> Unit) {
        userRepository.loginUser(email, password, onResult)
    }
}