package com.karimgabbasov.a65apps.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MapDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addContact(contact: MapEntity)

    @Delete
    fun deleteContact(contact: MapEntity)

    @Query("SELECT * FROM contacts WHERE id = :contactId")
    fun getContactByID(contactId: String): MapEntity

    @Query("SELECT * FROM contacts")
    fun getAllContacts(): List<MapEntity>
}
