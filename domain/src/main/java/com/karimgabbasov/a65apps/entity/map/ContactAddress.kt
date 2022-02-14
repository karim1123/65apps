package com.karimgabbasov.a65apps.entity.map

interface ContactAddress {
    fun getContactAddress(latitude: Double, longitude: Double): String
}