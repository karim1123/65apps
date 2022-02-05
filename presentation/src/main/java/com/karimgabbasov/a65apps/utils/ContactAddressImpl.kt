package com.karimgabbasov.a65apps.utils

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.widget.Toast
import com.karimgabbasov.a65apps.R
import com.karimgabbasov.a65apps.entity.map.ContactAddress
import java.io.IOException
import java.util.Locale

class ContactAddressImpl(private val context: Context) : ContactAddress {
    override fun getContactAddress(latitude: Double, longitude: Double): String {
        val mGeocoder = Geocoder(context, Locale.getDefault())
        var addressString = ""
        try {
            val addressList: List<Address> =
                mGeocoder.getFromLocation(latitude, longitude, 1)
            if (addressList.isNotEmpty()) {
                val address = addressList[0]
                val sb = StringBuilder()
                for (i in 0 until address.maxAddressLineIndex) {
                    sb.append(address.getAddressLine(i)).append("\n")
                }
                val setAddress = { contactAddress: String?, stringBuilder: StringBuilder ->
                    if (contactAddress != null) {
                        if (contactAddress != address.postalCode) stringBuilder.append(
                            contactAddress
                        ).append(", ") else stringBuilder.append(contactAddress)
                    }
                }
                setAddress(address.thoroughfare, sb)
                setAddress(address.subThoroughfare, sb)
                setAddress(address.subLocality, sb)
                setAddress(address.subAdminArea, sb)
                setAddress(address.adminArea, sb)
                setAddress(address.countryName, sb)
                setAddress(address.postalCode, sb)
                addressString = sb.toString()
            }
        } catch (e: IOException) {
            Toast.makeText(context, R.string.geocoder_error, Toast.LENGTH_LONG).show()
        }
        return addressString
    }
}
