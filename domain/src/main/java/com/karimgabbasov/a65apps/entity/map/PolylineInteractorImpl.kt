package com.karimgabbasov.a65apps.entity.map

class PolylineInteractorImpl : PolylineInteractor {
    override fun decodePolyline(encoded: String): List<CoordinatesModel> {
        val poly = ArrayList<CoordinatesModel>()
        var index = 0
        val len = encoded.length
        var lat = 0
        var lng = 0
        while (index < len) {
            var b: Int
            var shift = 0
            var result = 0
            do {
                b = encoded[index++].code - 63
                result = result or (b and UNIT_SEPARATOR shl shift)
                shift += 5
            } while (b >= HEXADECIMAL_THIRTY_TWO)
            val dlat = if (result and 1 != 0) (result shr 1).inv() else result shr 1
            lat += dlat
            shift = 0
            result = 0
            do {
                b = encoded[index++].code - 63
                result = result or (b and UNIT_SEPARATOR shl shift)
                shift += 5
            } while (b >= HEXADECIMAL_THIRTY_TWO)
            val dlng = if (result and 1 != 0) (result shr 1).inv() else result shr 1
            lng += dlng
            val latLng = CoordinatesModel(
                (lat.toDouble() / ONE_HUNDRED_THOUSAND),
                (lng.toDouble() / ONE_HUNDRED_THOUSAND)
            )
            poly.add(latLng)
        }
        return poly
    }

    companion object {
        const val HEXADECIMAL_THIRTY_TWO = 0x20
        const val ONE_HUNDRED_THOUSAND = 1E5
        const val UNIT_SEPARATOR = 0x1f
    }
}
// код позаимствован с https://www.geeksforgeeks.org/how-to-generate-route-between-two-locations-in-google-map-in-android/
