package com.karimgabbasov.a65apps.interactors.viewmodel

import com.karimgabbasov.a65apps.entity.contactmodels.ContactsListModel
import com.karimgabbasov.a65apps.entity.contactmodels.DetailedContactModel

interface ContactDataSourceInteractor {
    fun getContactListData(query: String): List<ContactsListModel>
    fun getContactData(id: String): List<DetailedContactModel>
}
