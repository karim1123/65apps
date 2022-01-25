package com.karimgabbasov.a65apps.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.karimgabbasov.a65apps.R
import com.karimgabbasov.a65apps.entity.contactmodels.DetailedContactModel
import com.karimgabbasov.a65apps.interactors.birthday.BirthdayNotificationInteractorImpl
import com.karimgabbasov.a65apps.interactors.viewmodel.ContactDetailsInteractorImpl
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.CompositeDisposable
import java.util.*
import javax.inject.Inject
import javax.inject.Named

class ContactDetailsViewModel @Inject constructor(
    private val repository: ContactDetailsInteractorImpl,
    private val birthdayNotificationInteractorImpl: BirthdayNotificationInteractorImpl,
    @Named(subscribeOnSchedulerQualifier) private val subscribeOnScheduler: Scheduler,
    @Named(observeOnSchedulerQualifier) private val observeOnScheduler: Scheduler
) :
    ViewModel() {
    private val mutableContactDetails = MutableLiveData<List<DetailedContactModel>>()
    private val mutableProgressIndicatorStatus = MutableLiveData<Boolean>()
    val contactDetails = mutableContactDetails as LiveData<List<DetailedContactModel>>
    val progressIndicatorStatus = mutableProgressIndicatorStatus as LiveData<Boolean>
    private val disposable: CompositeDisposable = CompositeDisposable()

    override fun onCleared() {
        disposable.dispose()
        super.onCleared()
    }

    fun loadDetailContact(id: String) {
        if (mutableContactDetails.value == null) {
            disposable.add(
                Single.fromCallable {
                    repository.getContactData(id)
                }
                    .subscribeOn(subscribeOnScheduler)
                    .observeOn(observeOnScheduler)
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

    fun changeNotifyStatus(notifyStatus: Boolean, currentDate: Calendar) {
        val contact = mutableContactDetails.value?.get(0)
        if (contact != null) {
            if (notifyStatus) {
                birthdayNotificationInteractorImpl.setNotification(
                    contact,
                    currentDate
                )
            } else {
                birthdayNotificationInteractorImpl.cancelNotification(contact)
            }
        }
    }

    companion object {
        const val observeOnSchedulerQualifier = "ObserveOnScheduler"
        const val subscribeOnSchedulerQualifier = "SubscribeOnScheduler"
    }
}