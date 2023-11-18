package com.tprobius.hotelbookingapp.features.booking.presentation.bookinginfofragment

import com.tprobius.hotelbookingapp.features.booking.domain.model.BookingInfoModel

sealed interface BookingInfoState {
    object Initial : BookingInfoState
    object Loading : BookingInfoState
    data class Success(val bookingInfo: BookingInfoModel) : BookingInfoState
    object Error : BookingInfoState
}