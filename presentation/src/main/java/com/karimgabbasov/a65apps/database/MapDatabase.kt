package com.karimgabbasov.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [MapEntity::class], version = 1)
abstract class MapDatabase : RoomDatabase() {
    abstract fun contactDao(): MapDao
}