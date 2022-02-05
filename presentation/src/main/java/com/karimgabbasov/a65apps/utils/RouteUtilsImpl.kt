package com.karimgabbasov.a65apps.utils

import android.content.Context
import android.widget.Toast
import com.google.android.gms.maps.model.LatLng
import com.google.gson.Gson
import com.karimgabbasov.a65apps.R
import com.karimgabbasov.a65apps.entity.map.MapData
import com.karimgabbasov.a65apps.entity.map.PolylineInteractor
import javax.inject.Inject
import okhttp3.OkHttpClient
import okhttp3.Request

class RouteUtilsImpl @Inject constructor(
    private val context: Context,
    private val polylineInteractorImpl: PolylineInteractor
) : RouteUtils {
    override fun getPolylines(
        originLocation: LatLng,
        destinationLocation: LatLng,
        apiKey: String
    ): ArrayList<List<LatLng>> {
        val url = getDirectionURL(originLocation, destinationLocation, apiKey)
        val client = OkHttpClient()
        val request = Request.Builder().url(url).build()
        val response = client.newCall(request).execute()
        val data = response.body?.string()
        val result = ArrayList<List<LatLng>>()
        try {
            val respObj = Gson().fromJson(data, MapData::class.java)
            val path = ArrayList<LatLng>()
            for (i in 0 until respObj.routes[0].legs[0].steps.size) {
                path.addAll(
                    polylineInteractorImpl.decodePolyline(respObj.routes[0].legs[0].steps[i].polyline.points)
                        .map {
                            LatLng(it.latitude, it.longitude)
                        }
                )
            }
            result.add(path)
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(context, R.string.route_error, Toast.LENGTH_SHORT).show()
        }
        return result
    }

    override fun getDirectionURL(origin: LatLng, dest: LatLng, secret: String): String {
        return context.getString(
            R.string.route_request,
            origin.latitude.toString(),
            origin.longitude.toString(),
            dest.latitude.toString(),
            dest.longitude.toString(),
            secret
        )
    }
}
