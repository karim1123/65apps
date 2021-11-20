package com.karimgabbasov.a65apps.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.karimgabbasov.a65apps.data.DetailedContactModel
import com.karimgabbasov.a65apps.model.ContactsDataSource
import kotlin.concurrent.thread

class ContactDetailViewModel : ViewModel() {

    private val mutableContactDetails  = MutableLiveData<List<DetailedContactModel>>()
    val contactDetails = mutableContactDetails as LiveData<List<DetailedContactModel>>

    fun loadDetailContact(context: Context, id: String) {
        thread(start = true) {
            mutableContactDetails.postValue(ContactsDataSource.getContactData(context, id))
        }
    }
}
