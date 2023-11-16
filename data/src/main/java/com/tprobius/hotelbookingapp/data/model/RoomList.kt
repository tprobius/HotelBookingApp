package com.tprobius.hotelbookingapp.data.model


import com.google.gson.annotations.SerializedName
import com.tprobius.hotelbookingapp.features.room.domain.model.RoomListModel

data class RoomList(
    @SerializedName("rooms")
    val rooms: List<RoomInfo?>?
) {
    fun mapToRoomListModel() = RoomListModel(rooms?.map { it?.mapTooRoomInfoModel() })
}