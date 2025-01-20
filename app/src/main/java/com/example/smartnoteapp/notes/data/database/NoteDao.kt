package com.example.smartnoteapp.notes.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.smartnoteapp.notes.data.models.CategoryEntity
import com.example.smartnoteapp.notes.data.models.NoteCategoryCrossRef
import com.example.smartnoteapp.notes.data.models.NoteEntity
import com.example.smartnoteapp.notes.data.models.relations.NoteWithCategories

@Dao
interface NoteDao {

    // INSERT
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNote(note: NoteEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCategory(category: CategoryEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNoteCategoryCrossRef(crossRef: NoteCategoryCrossRef)

    // UPDATE
    @Update
    fun updateNote(note: NoteEntity)

    // DELETE
    @Query("DELETE FROM notes WHERE id = :noteId")
    fun deleteNote(noteId: Long)

    @Query("DELETE FROM notes")
    fun deleteAllNotes()

    @Query("DELETE FROM note_categories_refs")
    suspend fun deleteAllNoteCategoryRefs()

    @Query("DELETE FROM categories")
    suspend fun deleteAllCategories()

    // SELECT (GET)
    @Query("SELECT * FROM notes WHERE id = :noteId")
    fun getNoteWithCategoriesById(noteId: Long): NoteWithCategories

    @Transaction
    @Query("SELECT * FROM notes")
    fun getAllNotesWitCategories(): List<NoteWithCategories>

    @Transaction
    @Query("SELECT * FROM categories")
    fun getAllCategories(): List<CategoryEntity>

    @Transaction
    @Query("SELECT * FROM note_categories_refs")
    fun getAllNoteCategoryRefs(): List<NoteCategoryCrossRef>
}