package com.tprobius.hotelbookingapp.features.booking.presentation.bookinginfofragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tprobius.hotelbookingapp.features.booking.domain.usecases.GetBookingInfoUseCase
import com.tprobius.hotelbookingapp.features.booking.presentation.bookinginfofragment.BookingInfoState.Error
import com.tprobius.hotelbookingapp.features.booking.presentation.bookinginfofragment.BookingInfoState.Initial
import com.tprobius.hotelbookingapp.features.booking.presentation.bookinginfofragment.BookingInfoState.Loading
import com.tprobius.hotelbookingapp.features.booking.presentation.bookinginfofragment.BookingInfoState.Success
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class BookingInfoViewModel(
    private val getBookingInfoUseCase: GetBookingInfoUseCase,
    private val router: BookingInfoRouter
) : ViewModel() {
    private val _state: MutableStateFlow<BookingInfoState> = MutableStateFlow(Initial)
    val state: StateFlow<BookingInfoState> = _state

    fun getBookingInfo() {
        viewModelScope.launch {
            _state.value = Loading
            try {
                getBookingInfoUseCase().collect {
                    _state.value = Success(it)
                }
            } catch (e: Exception) {
                _state.value = Error
            }
        }
    }

    fun openPaymentInfo() {
        router.openPaymentInfo()
    }

    fun backToRoomInfo(hotelName: String) {
        router.backToRoomInfo(hotelName)
    }
}