package com.example.smartnoteapp.auth.domain.repository

import com.example.smartnoteapp.auth.domain.models.User
import com.google.firebase.auth.FirebaseUser

interface IUserRepository {

    fun loginUser(email: String, password: String, onResult: (Result<FirebaseUser?>) -> Unit)

    fun signUpUser(email: String, password: String, onResult: (Result<FirebaseUser?>) -> Unit)

    fun signOutUser()

    fun getCurrentUser(): FirebaseUser?

    suspend fun getCurrentUserInfo(): Result<User?>
}