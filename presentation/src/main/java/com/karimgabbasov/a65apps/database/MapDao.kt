package com.karimgabbasov.database

import androidx.room.*

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
