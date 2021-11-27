package com.karimgabbasov.a65apps.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ContactDetailViewModelFactory(val application: Application, val contactId: String) :
    ViewModelProvider.AndroidViewModelFactory(application) {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return ContactDetailViewModel(
            application, contactId
        ) as T
    }
}