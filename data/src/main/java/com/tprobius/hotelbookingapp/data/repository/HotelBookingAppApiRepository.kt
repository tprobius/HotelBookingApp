package com.tprobius.hotelbookingapp.data.repository

import com.tprobius.hotelbookingapp.data.model.BookingInfo
import kotlinx.coroutines.flow.Flow

interface HotelBookingAppApiRepository {
    suspend fun getBookingInfo(): Flow<BookingInfo>
}