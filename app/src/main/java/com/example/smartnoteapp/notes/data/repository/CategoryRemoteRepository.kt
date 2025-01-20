package com.example.smartnoteapp.notes.data.repository

import android.util.Log
import com.example.smartnoteapp.notes.domain.models.Category
import com.example.smartnoteapp.notes.domain.repository.ICategoryRemoteRepository
import com.example.smartnoteapp.notes.utils.NoteConstants.CATEGORIES_COLLECTION
import com.example.smartnoteapp.notes.utils.NoteConstants.LOG_FIREBASE_FIRESTORE
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class CategoryRemoteRepository: ICategoryRemoteRepository {
    private val firestoreDb = Firebase.firestore
    private val categoriesCollection = firestoreDb.collection(CATEGORIES_COLLECTION)

    override suspend fun getCategoryByName(categoryName: String): Category? {
        return try {
            // Perform the query and await the result
            val result = categoriesCollection
                .whereEqualTo("name", categoryName)
                .get()
                .await()  // Wait for the result

            if (result.documents.isNotEmpty()) {
                result.documents[0].toObject<Category>()  // Return the first category found
            } else {
                null  // Return null if no category found
            }
        } catch (e: Exception) {
            Log.d(LOG_FIREBASE_FIRESTORE, "Error getting categories", e)
            null  // Return null in case of an error
        }
    }

    override suspend fun getAllCategories(): List<Category> {
        val categoriesList = mutableListOf<Category>()
        categoriesCollection
            .get()
            .addOnSuccessListener { result ->
                for (document in result.documents) {
                    val category = document.toObject(Category::class.java)
                    category?.let {
                        categoriesList.add(it)
                    }
                }
                Log.d(LOG_FIREBASE_FIRESTORE, "Categories are retrieved")
            }
            .addOnFailureListener { e ->
                Log.d(LOG_FIREBASE_FIRESTORE, "Error getting categories", e)
            }
        return categoriesList
    }
}