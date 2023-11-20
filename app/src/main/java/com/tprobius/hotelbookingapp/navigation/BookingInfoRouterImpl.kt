package com.tprobius.hotelbookingapp.navigation

import com.github.terrakok.cicerone.Router
import com.tprobius.hotelbookingapp.features.booking.presentation.bookinginfofragment.BookingInfoRouter
import com.tprobius.hotelbookingapp.features.payment.paymentinfofragment.getPaymentInfoScreen
import com.tprobius.hotelbookingapp.features.room.presentation.roomlistfragment.getRoomInfoScreen

class BookingInfoRouterImpl(
    private val router: Router
) : BookingInfoRouter {
    override fun openPaymentInfo() {
        router.navigateTo(getPaymentInfoScreen())
    }

    override fun backToRoomInfo(hotelName: String) {
        router.backTo(getRoomInfoScreen(hotelName))
    }
}