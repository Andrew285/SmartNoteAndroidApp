package com.example.smartnoteapp.notes.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.smartnoteapp.notes.domain.models.Note

@Dao
interface NoteDao {
    @Insert
    fun insert(note: Note)

    @Update
    fun update(note: Note)

    @Delete
    fun delete(noteId: Int)

    @Query("SELECT * FROM notes WHERE id = :noteId")
    fun getNoteById(noteId: Int)

    @Query("SELECT * FROM notes")
    fun getAllNotes()
}