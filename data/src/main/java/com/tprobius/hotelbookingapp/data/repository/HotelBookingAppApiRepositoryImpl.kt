package com.tprobius.hotelbookingapp.data.repository

import com.tprobius.hotelbookingapp.data.model.BookingInfo
import com.tprobius.hotelbookingapp.data.model.HotelInfo
import com.tprobius.hotelbookingapp.data.model.RoomList
import com.tprobius.hotelbookingapp.data.source.HotelBookingAppApi
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class HotelBookingAppApiRepositoryImpl(
    private val hotelBookingAppApi: HotelBookingAppApi,
    private val dispatcher: CoroutineDispatcher
) : HotelBookingAppApiRepository {
    override suspend fun getHotelInfo(): Flow<HotelInfo> {
        return withContext(dispatcher) { hotelBookingAppApi.getHotelInfo() }
    }

    override suspend fun getRoomList(): Flow<RoomList> {
        return withContext(dispatcher) { hotelBookingAppApi.getRoomList() }
    }

    override suspend fun getBookingInfo(): Flow<BookingInfo> {
        return withContext(dispatcher) { hotelBookingAppApi.getBookingInfo() }
    }
}