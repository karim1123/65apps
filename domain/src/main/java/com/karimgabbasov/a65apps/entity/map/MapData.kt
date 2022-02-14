package com.karimgabbasov.a65apps.entity.map

data class MapData(
    val routes: ArrayList<Routes> = ArrayList()
)

data class Routes(
    val legs: ArrayList<Legs> = ArrayList()
)

data class Legs(
    val distance: Distance = Distance(),
    val duration: Duration = Duration(),
    val end_address: String = "",
    val start_address: String = "",
    val end_location: Location = Location(),
    val start_location: Location = Location(),
    val steps: ArrayList<Steps> = ArrayList()
)

data class Steps(
    val distance: Distance = Distance(),
    val duration: Duration = Duration(),
    val end_address: String = "",
    val start_address: String = "",
    val end_location: Location = Location(),
    val start_location: Location = Location(),
    val polyline: PolyLine = PolyLine(),
    val travel_mode: String = "",
    val maneuver: String = ""
)

data class Duration(
    val text: String = "",
    val value: Int = 0
)

data class Distance(
    val text: String = "",
    val value: Int = 0
)

data class PolyLine(
    val points: String = ""
)

data class Location(
    val lat: String = "",
    val lng: String = ""
)
