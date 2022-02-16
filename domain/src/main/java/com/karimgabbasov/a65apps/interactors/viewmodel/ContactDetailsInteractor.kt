package com.karimgabbasov.a65apps.interactors.viewmodel

import com.karimgabbasov.a65apps.entity.contactmodels.DetailedContactModel

interface ContactDetailsInteractor {
    fun getContactData(id: String): List<DetailedContactModel>
}
