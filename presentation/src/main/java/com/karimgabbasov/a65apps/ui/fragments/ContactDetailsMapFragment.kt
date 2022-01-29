package com.karimgabbasov.a65apps.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.karimgabbasov.a65apps.R
import com.karimgabbasov.a65apps.databinding.FragmentContactDetailsMapBinding
import com.karimgabbasov.a65apps.di.api.AppContainer
import com.karimgabbasov.a65apps.di.injectViewModel
import com.karimgabbasov.a65apps.entity.map.ContactAddress
import com.karimgabbasov.a65apps.entity.map.MapModel
import com.karimgabbasov.a65apps.viewmodel.ContactDetailsMapViewModel
import javax.inject.Inject

class ContactDetailsMapFragment : Fragment(), OnMapReadyCallback {

    private var _binding: FragmentContactDetailsMapBinding? = null
    private val binding get() = _binding!!
    private var map: GoogleMap? = null
    private lateinit var contactId: String
    private lateinit var viewModelContactDetailsMap: ContactDetailsMapViewModel

    @Inject
    lateinit var factory: ViewModelProvider.Factory

    @Inject
    lateinit var contactAddress: ContactAddress

    override fun onAttach(context: Context) {
        super.onAttach(context)
        contactId = arguments?.getString(ARGUMENT_ID).toString()
        (activity?.application as AppContainer).getAppComponent()
            ?.plusContactDetailsMapComponent()
            ?.inject(this)
        viewModelContactDetailsMap = injectViewModel(factory)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentContactDetailsMapBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)

        viewModelContactDetailsMap.getLocation(contactId)
        viewModelContactDetailsMap.mutableLocation.observe(viewLifecycleOwner) {
            getSavedLocation(it)
        }

        binding.btnSaveLocation.setOnClickListener {
            viewModelContactDetailsMap.saveLocation()
            Toast.makeText(context, R.string.save_location, Toast.LENGTH_SHORT).show()
        }
        binding.btnDeleteLocation.setOnClickListener {
            deleteLocation()
        }
        (activity as AppCompatActivity).supportActionBar?.title =
            getString(R.string.contact_details_map_fragment_title)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        map?.let { setMapLongClick(it) }
    }

    private fun setMapLongClick(map: GoogleMap) {
        map.setOnMapLongClickListener { latLng ->
            map.clear()
            map.addMarker(
                MarkerOptions()
                    .position(latLng)
            )
            val addressString = setContactAddress(latLng)
            viewModelContactDetailsMap.mutableLocation.postValue(
                MapModel(
                    contactId,
                    latLng.latitude,
                    latLng.longitude,
                    addressString
                )
            )
        }
    }

    private fun getSavedLocation(mapModel: MapModel) {
        if (mapModel.address != EMPTY_STRING) {
            val latLng = LatLng(mapModel.latitude, mapModel.longitude)
            map?.addMarker(
                MarkerOptions()
                    .position(latLng)
            )
            map?.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, ZOOMLEVEL))
            binding.addressEditText.setText(mapModel.address)
        }
    }

    private fun deleteLocation() {
        viewModelContactDetailsMap.deleteLocation()
        map?.clear()
        binding.addressEditText.setText(EMPTY_STRING)
        viewModelContactDetailsMap.mutableLocation.postValue(
            MapModel(
                contactId,
                0.0,
                0.0,
                EMPTY_STRING
            )
        )
        Toast.makeText(context, R.string.delete_location, Toast.LENGTH_SHORT).show()
    }

    fun setContactAddress(latLng: LatLng): String {
        val addressString = contactAddress.getContactAddress(
            latLng.latitude,
            latLng.longitude
        )
        binding.addressEditText.setText(addressString)
        return addressString
    }

    companion object {
        private const val EMPTY_STRING = ""
        private const val ARGUMENT_ID = "id"
        private const val ZOOMLEVEL = 10f
        fun getNewInstance(id: String): ContactDetailsMapFragment =
            ContactDetailsMapFragment().apply {
                arguments = bundleOf(
                    ARGUMENT_ID to id
                )
            }
    }
}