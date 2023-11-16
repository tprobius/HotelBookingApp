package com.tprobius.hotelbookingapp.features.hotel.domain.model

import com.tprobius.hotelbookingapp.features.hotel.presentation.adapterdelegates.ListItem

data class AboutTheHotelModel(
    val description: String?,
    val peculiarities: List<String?>?
) : ListItem
