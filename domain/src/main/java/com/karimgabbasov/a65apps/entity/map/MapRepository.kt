package com.karimgabbasov.a65apps.entity.map

interface MapRepository {
    fun addContact(contact: MapModel)

    fun deleteContact(contact: MapModel)

    fun getContactById(contactId: String): MapModel

    fun getAllContacts(): List<MapModel>
}
