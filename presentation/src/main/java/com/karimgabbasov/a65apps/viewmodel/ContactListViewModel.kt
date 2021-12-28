package com.karimgabbasov.a65apps.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.karimgabbasov.a65apps.R
import com.karimgabbasov.a65apps.entity.contactmodels.ContactsListModel
import com.karimgabbasov.a65apps.interactors.viewmodel.ContactListInteractorImpl
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class ContactListViewModel @Inject constructor(private val repository: ContactListInteractorImpl) :
    ViewModel() {
    private val mutableContactList = MutableLiveData<List<ContactsListModel>>()
    private val mutableProgressIndicatorStatus = MutableLiveData<Boolean>()
    val contactList = mutableContactList as LiveData<List<ContactsListModel>>
    val progressIndicatorStatus = mutableProgressIndicatorStatus as LiveData<Boolean>
    private val disposable: CompositeDisposable = CompositeDisposable()

    init {
        loadContacts(EMPTY_QUERY)
    }

    fun loadContacts(query: String) {
        disposable.add(
            Single.fromCallable {
                repository.getContactListData(query)
            }
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
        )
    }

    override fun onCleared() {
        disposable.dispose()
        super.onCleared()
    }

    companion object {
        const val EMPTY_QUERY = ""
    }
}