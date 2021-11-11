package com.karimgabbasov.a65apps.data

data class DetailedContactModel(
    val id: String,
    val name: String,
    val number: String,
    val secondPhoneNumber: String,
    val mail: String,
    val secondMail: String,
    val description: String,
    val birthday: String,
    val image: String?
)
