package com.karimgabbasov.a65apps.utils

import com.google.android.gms.maps.model.LatLng

interface RouteUtils {
    fun getPolylines(
        originLocation: LatLng,
        destinationLocation: LatLng,
        apiKey: String,
    ): ArrayList<List<LatLng>>

    fun getDirectionURL(origin: LatLng, dest: LatLng, secret: String): String
}