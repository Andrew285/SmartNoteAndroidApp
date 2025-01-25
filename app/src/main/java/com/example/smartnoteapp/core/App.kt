package com.example.smartnoteapp.core

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.room.Room
import androidx.work.Configuration
import com.example.smartnoteapp.core.data.database.AppDatabase
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class App : Application(), Configuration.Provider {
    companion object {
        lateinit var database: AppDatabase
    }

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()

    @Inject
    lateinit var workerFactory : HiltWorkerFactory
//
//    override fun getWorkManagerConfiguration(): Configuration {
//        return Configuration.Builder()
//            .setWorkerFactory(workerFactory)
//            .build()
//    }

    override fun onCreate() {
        super.onCreate()
        database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "my_database"
        ).build()
    }
}
