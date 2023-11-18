package com.tprobius.hotelbookingapp.features.room.domain.repository

import com.tprobius.hotelbookingapp.features.room.domain.model.RoomListModel
import kotlinx.coroutines.flow.Flow

interface RoomListApiRepository {
    fun getRoomList(): Flow<RoomListModel>
}