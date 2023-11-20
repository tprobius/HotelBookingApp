package com.tprobius.hotelbookingapp.navigation

import com.github.terrakok.cicerone.Router
import com.tprobius.hotelbookingapp.features.booking.presentation.bookinginfofragment.getBookingInfoScreen
import com.tprobius.hotelbookingapp.features.hotel.presentation.hotelinfofragment.getHotelInfoScreen
import com.tprobius.hotelbookingapp.features.room.presentation.roomlistfragment.RoomListRouter

class RoomListRouterImpl(
    private val router: Router
) : RoomListRouter {
    override fun openBookingInfo() {
        router.navigateTo((getBookingInfoScreen()))
    }

    override fun backToHotelInfo() {
        router.backTo(getHotelInfoScreen())
    }
}