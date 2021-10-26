package com.karimgabbasov.a65apps.data

import androidx.annotation.DrawableRes

data class ContactsModel(
    val firstName: String,
    val number: String,
    @DrawableRes val imageResourceId: Int,
)

