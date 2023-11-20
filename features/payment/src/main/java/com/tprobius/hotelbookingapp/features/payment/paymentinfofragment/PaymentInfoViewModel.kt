package com.tprobius.hotelbookingapp.features.payment.paymentinfofragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tprobius.hotelbookingapp.features.payment.paymentinfofragment.PaymentInfoState.Error
import com.tprobius.hotelbookingapp.features.payment.paymentinfofragment.PaymentInfoState.Initial
import com.tprobius.hotelbookingapp.features.payment.paymentinfofragment.PaymentInfoState.Loading
import com.tprobius.hotelbookingapp.features.payment.paymentinfofragment.PaymentInfoState.Success
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlin.random.Random
import kotlin.random.nextUInt

class PaymentInfoViewModel(
    private val router: PaymentInfoRouter
) : ViewModel() {
    private val _state: MutableStateFlow<PaymentInfoState> = MutableStateFlow(Initial)
    val state: StateFlow<PaymentInfoState> = _state

    fun getBookingNumber() {
        viewModelScope.launch {
            _state.value = Loading
            try {
                val bookingNumber = Random.nextUInt()
                _state.value = Success(bookingNumber.toString())
            } catch (e: Exception) {
                _state.value = Error
            }
        }
    }

    fun confirmBooking() {
        router.confirmPayment()
    }

    fun onBackPress() {
        router.onBackPress()
    }
}