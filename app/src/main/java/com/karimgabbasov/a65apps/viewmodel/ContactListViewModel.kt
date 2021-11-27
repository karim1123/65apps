package com.karimgabbasov.a65apps.viewmodel

import android.app.Application

import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.karimgabbasov.a65apps.R
import com.karimgabbasov.a65apps.data.ContactsModel
import com.karimgabbasov.a65apps.model.ContactsDataSource
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

class ContactListViewModel(application: Application) : AndroidViewModel(application) {
    private val mutableContactList = MutableLiveData<List<ContactsModel>>()
    private val mutableProgressIndicatorStatus = MutableLiveData<Boolean>()
    val contactList = mutableContactList as LiveData<List<ContactsModel>>
    val progressIndicatorStatus = mutableProgressIndicatorStatus as LiveData<Boolean>
    private lateinit var disposable: Disposable

    init {
        loadContacts(EMPTY_QUERY)
    }

    fun loadContacts(query: String) {
        val context by lazy { getApplication<Application>().applicationContext }
        disposable = ContactsDataSource.getContacts(context, query)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { mutableProgressIndicatorStatus.postValue(true) }
            .doFinally { mutableProgressIndicatorStatus.postValue(false) }
            .subscribe(
                { result -> mutableContactList.postValue(result) },
                {
                    Log.d(
                        R.string.data_loading_error.toString(),
                        R.string.contact_list_data_loading_error.toString()
                    )
                })

    }

    override fun onCleared() {
        disposable.dispose()
        super.onCleared()
    }

    companion object{
        const val EMPTY_QUERY = ""
    }
}