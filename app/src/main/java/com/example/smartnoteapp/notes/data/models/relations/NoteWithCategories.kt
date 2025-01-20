package com.example.smartnoteapp.notes.data.models.relations

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.smartnoteapp.notes.data.models.CategoryEntity
import com.example.smartnoteapp.notes.data.models.NoteCategoryCrossRef
import com.example.smartnoteapp.notes.data.models.NoteEntity


data class NoteWithCategories (
    @Embedded val note: NoteEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(
            value = NoteCategoryCrossRef::class,
            parentColumn = "noteId",
            entityColumn = "categoryId"
        )
    )
    val categories: List<CategoryEntity>
)