package com.example.smartnoteapp.notes.data.repository

import android.util.Log
import com.example.smartnoteapp.notes.data.models.converters.Converters.mapToRemote
import com.example.smartnoteapp.notes.domain.models.Note
import com.example.smartnoteapp.notes.domain.repository.INoteRemoteRepository
import com.example.smartnoteapp.notes.utils.NoteConstants.LOG_FIREBASE_FIRESTORE
import com.example.smartnoteapp.notes.utils.NoteConstants.NOTES_COLLECTION
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class NoteRemoteRepository: INoteRemoteRepository {
    private val firestoreDb = Firebase.firestore
    private val noteCollection = firestoreDb.collection(NOTES_COLLECTION)

    override suspend fun addNote(note: Note) {
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
    }

    override suspend fun editNote(note: Note) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteNote(noteId: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun getNoteById(noteId: Int): Note {
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
}