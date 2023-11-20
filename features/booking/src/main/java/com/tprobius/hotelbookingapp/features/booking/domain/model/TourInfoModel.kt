package com.tprobius.hotelbookingapp.features.booking.domain.model

import com.tprobius.hotelbookingapp.utils.recyclerviewadapter.ListItem

data class TourInfoModel(
    val arrivalCountry: String?,
    val departure: String?,
    val hotelName: String?,
    val numberOfNights: Int?,
    val nutrition: String?,
    val room: String?,
    val tourDateStart: String?,
    val tourDateStop: String?
) : ListItem