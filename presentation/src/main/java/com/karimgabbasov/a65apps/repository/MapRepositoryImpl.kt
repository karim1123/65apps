package com.karimgabbasov.a65apps.repository

import com.karimgabbasov.a65apps.database.MapDao
import com.karimgabbasov.a65apps.database.MapEntity
import com.karimgabbasov.a65apps.entity.map.MapModel
import com.karimgabbasov.a65apps.entity.map.MapRepository

class MapRepositoryImpl(private val mapDao: MapDao) : MapRepository {

    override fun addContact(contact: MapModel) {
        mapDao.addContact(
            contact.toMapEntity()
        )
    }

    override fun deleteContact(contact: MapModel) {
        mapDao.deleteContact(contact.toMapEntity())
    }

    override fun getContactById(contactId: String): MapModel {
        val location = mapDao.getContactByID(contactId)
        return location.toMapModel()
    }

    override fun getAllContacts(): List<MapModel> {
        return mapDao.getAllContacts().map { it.toMapModel() }
    }

    private fun MapModel.toMapEntity() = MapEntity(
        id = id,
        latitude = latitude,
        longitude = longitude,
        address = address
    )

    private fun MapEntity.toMapModel() = MapModel(
        id = id,
        latitude = latitude,
        longitude = longitude,
        address = address
    )
}
