package com.karimgabbasov.a65apps.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.karimgabbasov.a65apps.data.ContactsModel
import com.karimgabbasov.a65apps.model.ContactsDataSource
import kotlin.concurrent.thread

class ContactListViewModel : ViewModel() {
    private val mutableContactList = MutableLiveData<List<ContactsModel>>()
    val contactList = mutableContactList as LiveData<List<ContactsModel>>

    fun loadContacts(context: Context, query: String) {
        thread(start = true) {
            mutableContactList.postValue(ContactsDataSource.getContactListData(context, query))
        }
    }
}