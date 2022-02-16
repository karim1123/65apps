package com.karimgabbasov.a65apps.di.db

import android.content.Context
import androidx.room.Room
import com.karimgabbasov.a65apps.database.MapDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {
    @Singleton
    @Provides
    fun provideDatabase(context: Context) = Room.databaseBuilder(
        context, MapDatabase::class.java, "Database"
    ).build()
}
