package com.example.smartnoteapp.notes.domain.usecases.categories

import com.example.smartnoteapp.notes.domain.models.Category
import com.example.smartnoteapp.notes.domain.repository.ICategoryRemoteRepository
import javax.inject.Inject

class GetCategoryByNameUseCase @Inject constructor(
    private val categoriesRemoteRepository: ICategoryRemoteRepository
) {
    suspend operator fun invoke(name: String): Category? {
        return categoriesRemoteRepository.getCategoryByName(name)
    }
}