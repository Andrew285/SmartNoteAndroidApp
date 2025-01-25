package com.example.smartnoteapp.notes.data.models.converters

import com.example.smartnoteapp.notes.data.models.CategoryEntity
import com.example.smartnoteapp.notes.data.models.NoteEntity
import com.example.smartnoteapp.notes.data.models.relations.NoteWithCategories
import com.example.smartnoteapp.notes.data.models.remote.NoteRemote
import com.example.smartnoteapp.notes.domain.models.Category
import com.example.smartnoteapp.notes.domain.models.Note
import com.example.smartnoteapp.notes.utils.NoteConstants
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

object Converters {

    fun Note.mapToNoteEntity(): NoteEntity {
        return NoteEntity(
            title = this.title,
            description = this.description,
            timestamp = this.timestamp,
            image = this.image
        )
    }

    fun Note.mapToNoteWithCategories(): NoteWithCategories {
        val noteEntity = this.mapToNoteEntity()

        return NoteWithCategories(
            note = noteEntity,
            categories = this.categories.map { it.mapToCategoryEntity() }
        )
    }

    fun Note.mapToRemote(): NoteRemote {
        val firestoreDb = Firebase.firestore
        val categoryCollection = firestoreDb.collection(NoteConstants.CATEGORIES_COLLECTION)

        return NoteRemote(
            id = this.id,
            title = this.title,
            description = this.description,
            timestamp = this.timestamp,
            image = this.image,
            likesCount = this.likesCount,
            commentsCount = this.commentsCount,
            categories = this.categories.map { category ->
                categoryCollection.document(category.id.toString())
            }
        )
    }

    suspend fun NoteRemote.mapToNote(): Note {
        val firestoreDb = Firebase.firestore
        val categoryCollection = firestoreDb.collection(NoteConstants.CATEGORIES_COLLECTION)

        return Note(
            id = this.id,
            title = this.title,
            description = this.description,
            timestamp = this.timestamp,
            image = this.image,
            likesCount = this.likesCount,
            commentsCount = this.commentsCount,
            categories = this.categories!!.mapNotNull { categoryDocRef ->
                val categorySnapshot = categoryCollection.document(categoryDocRef.toString()).get().await()
                categorySnapshot.toObject(Category::class.java)
            }
        )
    }

    fun NoteWithCategories.mapToNote(): Note {
        return Note(
            id = this.note.id,
            title = this.note.title,
            description = this.note.description,
            timestamp = this.note.timestamp,
            image = this.note.image,
            categories = this.categories.map { it.mapToCategory() }
        )
    }

    fun Category.mapToCategoryEntity(): CategoryEntity {
        return CategoryEntity(
            id = this.id,
            name = this.name
        )
    }

    fun CategoryEntity.mapToCategory(): Category {
        return Category(
            id = this.id,
            name = this.name
        )
    }
}