package com.tprobius.hotelbookingapp.features.booking.domain.model

import com.tprobius.hotelbookingapp.utils.recyclerviewadapter.ListItem

data class TouristInfoModel(
    var touristCount: String? = "Первый",
    val firstName: String? = null,
    val lastName: String? = null,
    val dateOfBirth: String? = null,
    val citizenship: String? = null,
    val passport: String? = null,
    val passportExpirationDate: String? = null
) : ListItem