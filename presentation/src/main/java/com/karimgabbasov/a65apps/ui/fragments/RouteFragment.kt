package com.karimgabbasov.a65apps.ui.fragments

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import com.karimgabbasov.a65apps.R
import com.karimgabbasov.a65apps.databinding.FragmentRouteBinding
import com.karimgabbasov.a65apps.di.api.AppContainer
import com.karimgabbasov.a65apps.di.injectViewModel
import com.karimgabbasov.a65apps.viewmodel.RouteViewModel
import javax.inject.Inject

class RouteFragment : Fragment(), OnMapReadyCallback {
    private var _binding: FragmentRouteBinding? = null
    private val binding get() = _binding!!
    private var map: GoogleMap? = null
    private lateinit var viewModelRoute: RouteViewModel

    @Inject
    lateinit var factory: ViewModelProvider.Factory

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity?.application as AppContainer).getAppComponent()
            ?.plusRouteComponent()
            ?.inject(this)
        viewModelRoute = injectViewModel(factory)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRouteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)
        viewModelRoute.getLocations()
        viewModelRoute.loadContacts(EMPTY_STRING)
        restoreRoute()
        drawAllPins()
        binding.apply {
            btnRoute.setOnClickListener { onRouteBtn() }
            buildRouteBtn.setOnClickListener { onBuildRouteBtnClick() }
            closeRouteBtn.setOnClickListener { onCloseRouteBtn() }
        }
        (activity as AppCompatActivity).supportActionBar?.title =
            getString(R.string.route_fragment_title)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
    }

    private fun onRouteBtn() {
        binding.apply {
            fieldForFirstContact.isVisible = true
            fieldForSecondContact.isVisible = true
            buildRouteBtn.isVisible = true
            closeRouteBtn.isVisible = true
        }
    }

    private fun onCloseRouteBtn() {
        binding.apply {
            fieldForFirstContact.isVisible = false
            fieldForSecondContact.isVisible = false
            buildRouteBtn.isVisible = false
            closeRouteBtn.isVisible = false
            map?.clear()
            drawAllPins()
        }
        viewModelRoute.mutableOriginLocation.value = null
        viewModelRoute.mutableDestinationLocation.value = null
    }

    private fun onBuildRouteBtnClick() {
        val firstId = binding.fieldForFirstContact.text
        val secondId = binding.fieldForSecondContact.text
        val originLocation = checkFieldForId(firstId.toString())
        val destinationLocation = checkFieldForId(secondId.toString())
        if (originLocation != null && destinationLocation != null) {
            map?.clear()
            drawAllPins()
            viewModelRoute.mutableOriginLocation.postValue(originLocation)
            viewModelRoute.mutableDestinationLocation.postValue(destinationLocation)
            drawRoute(originLocation, destinationLocation)
        }
    }

    private fun restoreRoute() {
        val originLocation = viewModelRoute.mutableOriginLocation.value
        val destinationLocation = viewModelRoute.mutableDestinationLocation.value
        if (originLocation != null && destinationLocation != null) {
            drawRoute(originLocation, destinationLocation)
        }
    }

    private fun checkFieldForId(id: String?): LatLng? {
        var contactLatLng: LatLng? = null
        if (id != "") {
            viewModelRoute.mutableLocations.observe(viewLifecycleOwner) { locations ->
                val contact = locations.asSequence().map { it }.find { it.id == id }
                if (contact != null) {
                    contactLatLng = LatLng(contact.latitude, contact.longitude)
                } else Toast.makeText(context, R.string.wrong_first_id, Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(context, R.string.empty_first_id, Toast.LENGTH_SHORT).show()
        }
        return contactLatLng
    }

    private fun drawRoute(originLocation: LatLng, destinationLocation: LatLng) {
        val apiKey = getString(R.string.google_maps_key)
        viewModelRoute.resetPolylines()
        viewModelRoute.getPolylines(originLocation, destinationLocation, apiKey)
        val lineoption = PolylineOptions()
        viewModelRoute.mutablePolylines.observe(viewLifecycleOwner) {
            for (i in it.indices) {
                lineoption.addAll(it[i])
                lineoption.width(10f)
                lineoption.color(Color.RED)
                lineoption.geodesic(true)
            }
            map?.addPolyline(lineoption)
        }
        val latLngBuilder = LatLngBounds.Builder()
        latLngBuilder.include(originLocation)
        latLngBuilder.include(destinationLocation)
        val latLngBound = latLngBuilder.build()
        map?.animateCamera(CameraUpdateFactory.newLatLngBounds(latLngBound, PADDING))
    }

    private fun drawAllPins() {
        viewModelRoute.mutableLocations.observe(viewLifecycleOwner) { locations ->
            val latLngBuilder = LatLngBounds.Builder()
            locations.forEach { contactLocation ->
                latLngBuilder.include(LatLng(contactLocation.latitude, contactLocation.longitude))
                viewModelRoute.mutableContactList.observe(viewLifecycleOwner) { contacts ->
                    val contactName =
                        contacts.asSequence().map { it }.find { it.id == contactLocation.id }?.name
                    map?.addMarker(
                        MarkerOptions()
                            .position(LatLng(contactLocation.latitude, contactLocation.longitude))
                            .title("${contactLocation.id}. $contactName")
                            .snippet(contactLocation.address)
                    )
                }
            }
            val latLngBound = latLngBuilder.build()
            map?.animateCamera(CameraUpdateFactory.newLatLngBounds(latLngBound, PADDING))
        }
    }

    companion object {
        const val EMPTY_STRING = ""
        const val PADDING = 100
        fun getNewInstance(): RouteFragment {
            return RouteFragment()
        }
    }
}
