package com.karimgabbasov.a65apps.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.karimgabbasov.a65apps.entity.map.MapModel
import com.karimgabbasov.a65apps.entity.map.MapRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class ContactDetailsMapViewModel @Inject constructor(private val mapRepository: MapRepository) :
    ViewModel() {
    val mutableLocation = MutableLiveData<MapModel>()
    private val disposable: CompositeDisposable = CompositeDisposable()

    override fun onCleared() {
        disposable.dispose()
        super.onCleared()
    }

    fun getLocation(contactId: String) {
        disposable.add(
            Single.fromCallable {
                mapRepository.getContactById(contactId)
            }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { result -> mutableLocation.postValue(result) },
                    {
                        mutableLocation.postValue(
                            MapModel(
                                contactId,
                                0.0,
                                0.0,
                                EMPTY_STRING
                            )
                        )
                    })
        )
    }

    fun saveLocation() {
        disposable.add(
            Single.fromCallable {
                mutableLocation.value?.let { mapRepository.addContact(it) }
            }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
        )
    }

    fun deleteLocation() {
        disposable.add(
            Single.fromCallable {
                mutableLocation.value?.let { mapRepository.deleteContact(it) }
            }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
        )
    }

    companion object{
        private const val EMPTY_STRING = ""
    }
}