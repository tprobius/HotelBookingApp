package com.tprobius.hotelbookingapp.data.model


import com.google.gson.annotations.SerializedName

data class RoomList(
    @SerializedName("rooms")
    val rooms: List<RoomInfo?>?
)