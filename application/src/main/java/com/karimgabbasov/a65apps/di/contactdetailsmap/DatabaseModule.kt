package com.karimgabbasov.a65apps.di.contactdetailsmap

import android.content.Context
import androidx.room.Room
import com.karimgabbasov.database.MapDatabase
import dagger.Module
import dagger.Provides

@Module
class DatabaseModule {
    @Provides
    fun provideDatabase(context: Context) = Room.databaseBuilder(
        context, MapDatabase::class.java, "Database"
    ).build()
}


