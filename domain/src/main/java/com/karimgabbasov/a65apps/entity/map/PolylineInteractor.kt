package com.karimgabbasov.a65apps.entity.map

interface PolylineInteractor {
    fun decodePolyline(encoded: String): List<CoordinatesModel>
}