package com.karimgabbasov.a65apps.viewmodel

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.karimgabbasov.a65apps.R
import com.karimgabbasov.a65apps.data.DetailedContactModel
import com.karimgabbasov.a65apps.model.ContactsDataSource
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class ContactDetailViewModel @Inject constructor(private val repository: ContactsDataSource) :
    ViewModel() {
    private val mutableContactDetails = MutableLiveData<List<DetailedContactModel>>()
    private val mutableProgressIndicatorStatus = MutableLiveData<Boolean>()
    val contactDetails = mutableContactDetails as LiveData<List<DetailedContactModel>>
    val progressIndicatorStatus = mutableProgressIndicatorStatus as LiveData<Boolean>
    private lateinit var disposable: Disposable

    fun loadDetailContact(id: String) {
        if (mutableContactDetails.value == null) {
            disposable = repository.getContact(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { mutableProgressIndicatorStatus.postValue(true) }
                .doFinally { mutableProgressIndicatorStatus.postValue(false) }
                .subscribe(
                    { result -> mutableContactDetails.postValue(result) },
                    {
                        Log.d(
                            R.string.data_loading_error.toString(),
                            R.string.contact_details_data_loading_error.toString()
                        )
                    })
        }
    }

    override fun onCleared() {
        disposable.dispose()
        super.onCleared()
    }
}