package com.tprobius.hotelbookingapp.features.room.presentation.roomlistfragment

import com.tprobius.hotelbookingapp.features.room.domain.model.RoomListModel

sealed interface RoomListState {
    object Initial : RoomListState
    object Loading : RoomListState
    data class Success(val roomInfo: RoomListModel) : RoomListState
    object Error : RoomListState
}