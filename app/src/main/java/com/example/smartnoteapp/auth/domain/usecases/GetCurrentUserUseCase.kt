package com.example.smartnoteapp.auth.domain.usecases

import com.example.smartnoteapp.auth.domain.repository.IUserRepository
import com.google.firebase.auth.FirebaseUser
import javax.inject.Inject

class GetCurrentUserUseCase @Inject constructor(
    private val userRepository: IUserRepository
) {
    suspend operator fun invoke(): FirebaseUser? {
        return userRepository.getCurrentUser()
    }
}