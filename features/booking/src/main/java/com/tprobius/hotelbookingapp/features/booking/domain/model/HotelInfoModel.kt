package com.tprobius.hotelbookingapp.features.booking.domain.model

import com.tprobius.hotelbookingapp.utils.recyclerviewadapter.ListItem

data class HotelInfoModel(
    val hotelRating: Int?,
    val hotelAddress: String?,
    val hotelName: String?,
    val ratingName: String?
) : ListItem
