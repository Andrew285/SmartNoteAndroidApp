package com.example.smartnoteapp.auth.domain.usecases

import com.example.smartnoteapp.auth.domain.repository.IUserRepository
import javax.inject.Inject

class SignOutUseCase @Inject constructor(
    private val userRepository: IUserRepository
) {
    suspend operator fun invoke() {
        userRepository.signOutUser()
    }
}