package com.example.smartnoteapp.notes.presentation.note

import androidx.work.WorkerFactory
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class WorkerModule {

    @Binds
    abstract fun bindWorkerFactory(
        factory: MyWorkerFactory
    ): WorkerFactory
}