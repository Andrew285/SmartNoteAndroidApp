package com.example.smartnoteapp.auth.domain.usecases

import com.example.smartnoteapp.auth.domain.models.User
import com.example.smartnoteapp.auth.domain.repository.IUserRepository
import javax.inject.Inject

class GetUserInfoUseCase @Inject constructor(
    private val userRepository: IUserRepository
) {
    suspend operator fun invoke(): Result<User?> {
        return userRepository.getCurrentUserInfo()
    }
}