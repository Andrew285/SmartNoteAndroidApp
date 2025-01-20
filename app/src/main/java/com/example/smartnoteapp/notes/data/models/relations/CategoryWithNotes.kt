package com.example.smartnoteapp.notes.data.models.relations

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.smartnoteapp.notes.data.models.CategoryEntity
import com.example.smartnoteapp.notes.data.models.NoteCategoryCrossRef
import com.example.smartnoteapp.notes.data.models.NoteEntity

data class CategoryWithNotes (
    @Embedded val category: CategoryEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(
            value = NoteCategoryCrossRef::class,
            parentColumn = "categoryId",  // Explicitly map the junction's noteId
            entityColumn = "noteId"  // Explicitly map the junction's categoryId
        )
    )
    val notes: List<NoteEntity>
)