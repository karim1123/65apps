package com.karimgabbasov.a65apps.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng
import com.karimgabbasov.a65apps.entity.contactmodels.ContactsListModel
import com.karimgabbasov.a65apps.entity.map.MapModel
import com.karimgabbasov.a65apps.entity.map.MapRepository
import com.karimgabbasov.a65apps.interactors.viewmodel.ContactListInteractor
import com.karimgabbasov.a65apps.utils.RouteUtils
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class RouteViewModel @Inject constructor(
    private val mapRepository: MapRepository,
    private val repository: ContactListInteractor,
    private val routeUtilsImpl: RouteUtils
) : ViewModel() {
    val mutableLocations = MutableLiveData<List<MapModel>>()
    val mutableContactList = MutableLiveData<List<ContactsListModel>>()
    var mutablePolylines = MutableLiveData<ArrayList<List<LatLng>>>()
    val mutableDestinationLocation = MutableLiveData<LatLng>()
    val mutableOriginLocation = MutableLiveData<LatLng>()
    private val disposable: CompositeDisposable = CompositeDisposable()

    override fun onCleared() {
        disposable.dispose()
        super.onCleared()
    }

    fun getLocations() {
        disposable.add(
            Single.fromCallable {
                mapRepository.getAllContacts()
            }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { result -> mutableLocations.postValue(result) },
                    {
                        mutableLocations.postValue(
                            listOf(
                                MapModel(
                                    id = EMPTY_STRING,
                                    latitude = 0.0,
                                    longitude = 0.0,
                                    address = EMPTY_STRING
                                )
                            )
                        )
                    })
        )
    }

    fun loadContacts(query: String) {
        disposable.add(
            Single.fromCallable {
                repository.getContactListData(query)
            }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { result -> mutableContactList.postValue(result) }
        )
    }

    fun getPolylines(originLocation: LatLng, destinationLocation: LatLng, apiKey: String) {
        disposable.add(
            Single.fromCallable {
                routeUtilsImpl.getPolylines(originLocation, destinationLocation, apiKey)
            }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { result -> mutablePolylines.postValue(result) }
        )
    }

    fun resetPolylines() {
        mutablePolylines = MutableLiveData()
    }

    companion object {
        private const val EMPTY_STRING = ""
    }
}
