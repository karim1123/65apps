package com.karimgabbasov.a65apps.interactors.viewmodel

import com.karimgabbasov.a65apps.entity.contactmodels.ContactsListModel

class ContactListInteractorImpl(private val repository: ContactDataSourceInteractor) :
    ContactListInteractor {
    override fun getContactListData(query: String): List<ContactsListModel> {
        return repository.getContactListData(query)
    }
}