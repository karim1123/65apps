package com.karimgabbasov.a65apps.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contacts")
data class MapEntity(
    @PrimaryKey
    val id: String,
    val latitude: Double,
    val longitude: Double,
    val address: String
)
