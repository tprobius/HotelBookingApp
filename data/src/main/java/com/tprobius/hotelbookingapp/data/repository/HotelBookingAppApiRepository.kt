package com.tprobius.hotelbookingapp.data.repository

import com.tprobius.hotelbookingapp.data.model.BookingInfo
import com.tprobius.hotelbookingapp.data.model.HotelInfo
import com.tprobius.hotelbookingapp.data.model.RoomList
import kotlinx.coroutines.flow.Flow

interface HotelBookingAppApiRepository {
    suspend fun getHotelInfo(): Flow<HotelInfo>

    suspend fun getRoomList(): Flow<RoomList>

    suspend fun getBookingInfo(): Flow<BookingInfo>
}