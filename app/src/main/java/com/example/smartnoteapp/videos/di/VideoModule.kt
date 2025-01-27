package com.example.smartnoteapp.videos.di

import com.example.smartnoteapp.videos.data.repository.VideoRepository
import com.example.smartnoteapp.videos.domain.repository.IVideoRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class VideoModule {

    @Provides
    fun provideVideoRepository(): IVideoRepository {
        return VideoRepository()
    }
}