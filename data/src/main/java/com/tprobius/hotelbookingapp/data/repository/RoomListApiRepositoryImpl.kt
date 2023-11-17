package com.tprobius.hotelbookingapp.data.repository

import com.tprobius.hotelbookingapp.data.source.HotelBookingAppApi
import com.tprobius.hotelbookingapp.features.room.domain.model.RoomListModel
import com.tprobius.hotelbookingapp.features.room.domain.repository.RoomListApiRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RoomListApiRepositoryImpl(
    private val hotelBookingAppApi: HotelBookingAppApi,
    private val dispatcher: CoroutineDispatcher
) : RoomListApiRepository {
    override fun getRoomList(): Flow<RoomListModel> {
        return flow {
            emit(hotelBookingAppApi.getRoomList().mapToRoomListModel())
        }.flowOn(dispatcher)
    }
}