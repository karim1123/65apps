package com.karimgabbasov.a65apps.data

import androidx.annotation.DrawableRes

data class DetailedContactModel(
    val firstName: String,
    val number: String,
    val secondPhoneNumber: String,
    val mail: String,
    val secondMail: String,
    val description: String,
    @DrawableRes val imageResourceId: Int
)
