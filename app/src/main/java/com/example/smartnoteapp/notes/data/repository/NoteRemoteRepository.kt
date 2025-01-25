package com.example.smartnoteapp.notes.data.repository

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSourceFactory
import com.example.smartnoteapp.notes.data.models.converters.Converters.mapToRemote
import com.example.smartnoteapp.notes.data.models.remote.NoteRemote
import com.example.smartnoteapp.notes.data.sources.remote.NotesRemotePagingSource
import com.example.smartnoteapp.notes.domain.models.Note
import com.example.smartnoteapp.notes.domain.repository.INoteRemoteRepository
import com.example.smartnoteapp.notes.utils.NoteConstants.LOG_FIREBASE_FIRESTORE
import com.example.smartnoteapp.notes.utils.NoteConstants.NOTES_COLLECTION
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class NoteRemoteRepository: INoteRemoteRepository {
    private val firestoreDb = Firebase.firestore
    private val noteCollection = firestoreDb.collection(NOTES_COLLECTION)

    override suspend fun addNote(note: Note): Result<Unit> {
        return try {
            val documentId = note.id.toString()
            val noteRemote = note.mapToRemote()

            noteCollection
                .document(documentId)
                .set(noteRemote)
                .addOnSuccessListener {
                    Log.d(LOG_FIREBASE_FIRESTORE, "DocumentSnapshot added with ID: $documentId")
                }
                .addOnFailureListener { e ->
                    Log.w(LOG_FIREBASE_FIRESTORE, "Error adding document", e)
                }
            Result.success(Unit)

        } catch (e: Exception) {
            Result.failure(e)
        }
    }


    override suspend fun editNote(note: Note): Result<NoteRemote?> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteNote(noteId: Int): Result<NoteRemote?> {
        return try {
            val documentSnapshot = noteCollection
                    .whereEqualTo("id", noteId)
                    .get()
                    .await()

            var note: NoteRemote? = null
            if (documentSnapshot != null) {
                for (document in documentSnapshot.documents) {
                    note = document.toObject(NoteRemote::class.java)
                    noteCollection
                        .document(document.id)
                        .delete()
                }
            }

            Result.success(note)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getNoteById(noteId: Int): NoteRemote {
        TODO("Not yet implemented")
    }

    override suspend fun getAllNotes(): List<Note> {
        val notes: MutableList<Note> = mutableListOf()
        noteCollection
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val note = document.toObject(Note::class.java)
                    notes.add(note)
                    Log.d(LOG_FIREBASE_FIRESTORE, "${document.id} => ${document.data}")
                }
            }
            .addOnFailureListener { exception ->
                Log.w(LOG_FIREBASE_FIRESTORE, "Error getting documents.", exception)
            }
        return notes
    }

    override suspend fun getNotesPagingData(): Pager<Int, NoteRemote> {
        val query = noteCollection.orderBy("timestamp", Query.Direction.DESCENDING)
        return Pager(
            config = PagingConfig(10, 10, enablePlaceholders = false),
            pagingSourceFactory = PagingSourceFactory { NotesRemotePagingSource(query) }
        )
    }
}