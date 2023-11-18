package com.tprobius.hotelbookingapp.navigation

import com.github.terrakok.cicerone.Router
import com.tprobius.hotelbookingapp.features.hotel.presentation.hotelinfofragment.HotelInfoRouter
import com.tprobius.hotelbookingapp.features.room.presentation.roomlistfragment.getRoomInfoScreen

class HotelInfoRouterImpl(
    private val router: Router
) : HotelInfoRouter {
    override fun openRoomInfo(hotelName: String) {
        router.navigateTo(getRoomInfoScreen(hotelName))
    }
}