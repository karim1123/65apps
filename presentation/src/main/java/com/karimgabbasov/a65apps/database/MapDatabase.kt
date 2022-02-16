package com.karimgabbasov.a65apps.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [MapEntity::class], version = 1, exportSchema = false)
abstract class MapDatabase : RoomDatabase() {
    abstract fun contactDao(): MapDao
}
