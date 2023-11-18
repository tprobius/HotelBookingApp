package com.tprobius.hotelbookingapp.features.booking.domain.repository

import com.tprobius.hotelbookingapp.features.booking.domain.model.BookingInfoModel
import kotlinx.coroutines.flow.Flow

interface BookingInfoApiRepository {
    fun getBookingInfo(): Flow<BookingInfoModel>
}