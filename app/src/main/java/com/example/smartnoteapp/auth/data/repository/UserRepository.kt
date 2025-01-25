package com.example.smartnoteapp.auth.data.repository

import com.example.smartnoteapp.auth.domain.models.User
import com.example.smartnoteapp.auth.domain.repository.IUserRepository
import com.example.smartnoteapp.auth.utils.UserConstants.USER_COLLECTION
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class UserRepository: IUserRepository {
    var firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private val firestoreDb = Firebase.firestore
    private val usersCollection = firestoreDb.collection(USER_COLLECTION)

    override fun loginUser(
        email: String,
        password: String,
        onResult: (Result<FirebaseUser?>) -> Unit
    ) {
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val user = firebaseAuth.currentUser
                onResult(Result.success(user))
            }
            else {
                val exception = task.exception
                onResult(Result.failure(exception ?: Exception("Unknown login error")))
            }
        }
    }

    override fun signUpUser(
        email: String,
        password: String,
        onResult: (Result<FirebaseUser?>) -> Unit
    ) {
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val user = firebaseAuth.currentUser
                onResult(Result.success(user))
            }
            else {
                val exception = task.exception
                onResult(Result.failure(exception ?: Exception("Unknown signUp error")))
            }
        }
    }

    override fun signOutUser() {
        firebaseAuth.signOut()
    }

    override fun getCurrentUser(): FirebaseUser? {
        return firebaseAuth.currentUser
    }

    override suspend fun getCurrentUserInfo(): Result<User?> = withContext(Dispatchers.IO) {
        val currentUser = getCurrentUser()
            ?: return@withContext Result.failure(Exception("No authenticated user"))

        try {
            val querySnapshot = usersCollection
                .whereEqualTo("email", currentUser.email)
                .limit(1)
                .get()
                .await()

            when {
                querySnapshot.isEmpty -> Result.success(null)
                querySnapshot.documents.isNotEmpty() -> {
                    val userDocument = querySnapshot.documents.first()
                    val user = userDocument.toObject(User::class.java)
                    Result.success(user)
                }
                else -> Result.success(null)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}