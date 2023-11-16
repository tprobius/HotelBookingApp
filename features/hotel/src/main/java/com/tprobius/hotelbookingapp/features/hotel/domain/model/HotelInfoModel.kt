package com.tprobius.hotelbookingapp.features.hotel.domain.model

import com.tprobius.hotelbookingapp.utils.recyclerviewadapter.ListItem

data class HotelInfoModel(
    val id: Int?,
    val name: String?,
    val address: String?,
    val minimalPrice: Int?,
    val priceForIt: String?,
    val rating: Int?,
    val ratingName: String?,
    val imageUrls: List<String?>?,
    val aboutTheHotel: AboutTheHotelModel?
) : ListItem