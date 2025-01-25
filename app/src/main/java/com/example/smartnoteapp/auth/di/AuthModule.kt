package com.example.smartnoteapp.auth.di

import com.example.smartnoteapp.auth.data.repository.UserRepository
import com.example.smartnoteapp.auth.domain.repository.IUserRepository
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class AuthModule {

    @Provides
    fun provideFirebaseAuthInstance(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Provides
    fun provideUserRepository(): IUserRepository {
        return UserRepository()
    }
}