package com.tprobius.hotelbookingapp.features.booking.domain.model

import com.tprobius.hotelbookingapp.utils.recyclerviewadapter.ListItem

data class BookingInfoModel(
    val id: Int?,
//    HotelBookingInfoItem
    val hotelName: String?,
    val hotelAddress: String?,
    val hotelRating: Int?,
    val ratingName: String?,
//    TourInfoItem + hotelName
    val departure: String?,
    val arrivalCountry: String?,
    val tourDateStart: String?,
    val tourDateStop: String?,
    val numberOfNights: Int?,
    val room: String?,
    val nutrition: String?,
//    CostInfoItem
    val tourPrice: Int?,
    val fuelCharge: Int?,
    val serviceCharge: Int?
) : ListItem