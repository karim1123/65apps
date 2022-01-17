package com.karimgabbasov.a65apps.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.karimgabbasov.a65apps.R
import com.karimgabbasov.a65apps.entity.contactmodels.DetailedContactModel
import com.karimgabbasov.a65apps.interactors.viewmodel.ContactDetailsInteractorImpl
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class ContactDetailsViewModel @Inject constructor(private val repository: ContactDetailsInteractorImpl) :
    ViewModel() {
    private val mutableContactDetails = MutableLiveData<List<DetailedContactModel>>()
    private val mutableProgressIndicatorStatus = MutableLiveData<Boolean>()
    val contactDetails = mutableContactDetails as LiveData<List<DetailedContactModel>>
    val progressIndicatorStatus = mutableProgressIndicatorStatus as LiveData<Boolean>
    private val disposable: CompositeDisposable = CompositeDisposable()

    fun loadDetailContact(id: String) {
        if (mutableContactDetails.value == null) {
            disposable.add(
                Single.fromCallable {
                    repository.getContactData(id)
                }
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
            )
        }
    }

    override fun onCleared() {
        disposable.dispose()
        super.onCleared()
    }
}