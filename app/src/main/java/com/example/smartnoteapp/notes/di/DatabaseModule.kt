package com.example.smartnoteapp.notes.di

import android.content.Context
import androidx.room.Room
import com.example.smartnoteapp.core.data.database.AppDatabase
import com.example.smartnoteapp.notes.data.database.NoteDao
import com.example.smartnoteapp.notes.data.repository.CategoryRemoteRepository
import com.example.smartnoteapp.notes.data.repository.NoteRemoteRepository
import com.example.smartnoteapp.notes.data.repository.NoteRepository
import com.example.smartnoteapp.notes.domain.repository.ICategoryRemoteRepository
import com.example.smartnoteapp.notes.domain.repository.INoteRemoteRepository
import com.example.smartnoteapp.notes.domain.repository.INoteRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context = context,
            klass = AppDatabase::class.java,
            name = "my_database"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    fun provideNoteDao(database: AppDatabase): NoteDao {
        return database.noteDao()
    }

    @Provides
    fun provideNoteRepository(noteDao: NoteDao): INoteRepository {
        return NoteRepository(noteDao)
    }

    @Provides
    fun provideNoteRemoteRepository(): INoteRemoteRepository {
        return NoteRemoteRepository()
    }

    @Provides
    fun provideCategoryRemoteRepository(): ICategoryRemoteRepository {
        return CategoryRemoteRepository()
    }
}