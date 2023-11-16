package com.tprobius.hotelbookingapp.data.repository

import com.tprobius.hotelbookingapp.data.model.BookingInfo
import com.tprobius.hotelbookingapp.data.source.HotelBookingAppApi
import com.tprobius.hotelbookingapp.features.hotel.domain.model.HotelInfoModel
import com.tprobius.hotelbookingapp.features.hotel.domain.repository.HotelInfoApiRepository
import com.tprobius.hotelbookingapp.features.room.domain.model.RoomListModel
import com.tprobius.hotelbookingapp.features.room.domain.repository.RoomListApiRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

class HotelBookingAppApiRepositoryImpl(
    private val hotelBookingAppApi: HotelBookingAppApi,
    private val dispatcher: CoroutineDispatcher
) : HotelInfoApiRepository, RoomListApiRepository, HotelBookingAppApiRepository {
    override fun getHotelInfo(): Flow<HotelInfoModel> {
        return flow {
            emit(hotelBookingAppApi.getHotelInfo().mapToHotelInfoModel())
        }.flowOn(dispatcher)
    }

    override fun getRoomList(): Flow<RoomListModel> {
        return flow {
            emit(hotelBookingAppApi.getRoomList().mapToRoomListModel())
        }.flowOn(dispatcher)
    }

    override suspend fun getBookingInfo(): Flow<BookingInfo> {
        return withContext(dispatcher) { hotelBookingAppApi.getBookingInfo() }
    }
}