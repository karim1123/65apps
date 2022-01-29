package com.karimgabbasov.database

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