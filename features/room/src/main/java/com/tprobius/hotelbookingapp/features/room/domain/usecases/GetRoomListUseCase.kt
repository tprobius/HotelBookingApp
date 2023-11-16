package com.tprobius.hotelbookingapp.features.room.domain.usecases

import com.tprobius.hotelbookingapp.features.room.domain.model.RoomListModel
import com.tprobius.hotelbookingapp.features.room.domain.repository.RoomListApiRepository
import kotlinx.coroutines.flow.Flow

class GetRoomListUseCase(
    private val roomListApiRepository: RoomListApiRepository
) {
    operator fun invoke(): Flow<RoomListModel> {
        return roomListApiRepository.getRoomList()
    }
}