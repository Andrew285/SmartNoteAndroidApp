package com.example.smartnoteapp.notes.domain.repository

import com.example.smartnoteapp.notes.domain.models.Category

interface ICategoryRemoteRepository {

    suspend fun getCategoryByName(categoryName: String): Category?

    suspend fun getAllCategories(): List<Category>
}