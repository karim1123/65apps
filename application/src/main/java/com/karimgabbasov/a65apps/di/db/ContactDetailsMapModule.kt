package com.karimgabbasov.a65apps.di.db

import com.karimgabbasov.a65apps.database.MapDatabase
import com.karimgabbasov.a65apps.entity.map.MapRepository
import com.karimgabbasov.a65apps.repository.MapRepositoryImpl
import dagger.Module
import dagger.Provides

@Module
class ContactDetailsMapModule {
    @Provides
    fun provideMapRepository(database: MapDatabase): MapRepository =
        MapRepositoryImpl(database.contactDao())
}
