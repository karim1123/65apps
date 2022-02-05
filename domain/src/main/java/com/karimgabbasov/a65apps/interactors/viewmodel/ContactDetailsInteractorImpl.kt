package com.karimgabbasov.a65apps.interactors.viewmodel

import com.karimgabbasov.a65apps.entity.contactmodels.DetailedContactModel

class ContactDetailsInteractorImpl(private val repository: ContactDataSourceInteractor) :
    ContactDetailsInteractor {
    override fun getContactData(id: String): List<DetailedContactModel> {
        return repository.getContactData(id)
    }
}
