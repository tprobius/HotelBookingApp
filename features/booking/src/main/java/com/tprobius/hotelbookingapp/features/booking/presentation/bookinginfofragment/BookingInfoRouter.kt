package com.tprobius.hotelbookingapp.features.booking.presentation.bookinginfofragment

interface BookingInfoRouter {
    fun openPaymentInfo()
    fun backToRoomInfo(hotelName: String)
}