package com.tprobius.hotelbookingapp.navigation

import com.github.terrakok.cicerone.Router
import com.tprobius.hotelbookingapp.features.hotel.presentation.hotelinfofragment.getHotelInfoScreen
import com.tprobius.hotelbookingapp.features.payment.paymentinfofragment.PaymentInfoRouter

class PaymentInfoRouterImpl(
    private val router: Router
) : PaymentInfoRouter {
    override fun confirmPayment() {
        router.backTo(getHotelInfoScreen())
    }

    override fun onBackPress() {
        router.backTo(getHotelInfoScreen())
    }
}