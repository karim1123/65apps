package com.karimgabbasov.a65apps.interactors.viewmodel

import com.karimgabbasov.a65apps.entity.contactmodels.ContactsListModel

interface ContactListInteractor {
    fun getContactListData(query: String): List<ContactsListModel>
}
