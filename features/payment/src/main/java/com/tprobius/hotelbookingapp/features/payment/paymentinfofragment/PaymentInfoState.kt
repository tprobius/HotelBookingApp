package com.tprobius.hotelbookingapp.features.payment.paymentinfofragment

sealed interface PaymentInfoState {
    object Initial : PaymentInfoState
    object Loading : PaymentInfoState
    data class Success(val bookingNumber: String) : PaymentInfoState
    object Error : PaymentInfoState
}