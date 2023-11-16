package com.tprobius.hotelbookingapp.data.model


import com.google.gson.annotations.SerializedName
import com.tprobius.hotelbookingapp.features.room.domain.model.RoomInfoModel

data class RoomInfo(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("price")
    val price: Int?,
    @SerializedName("price_per")
    val pricePer: String?,
    @SerializedName("peculiarities")
    val peculiarities: List<String?>?,
    @SerializedName("image_urls")
    val imageUrls: List<String?>?
) {
    fun mapTooRoomInfoModel() = RoomInfoModel(id, name, price, pricePer, peculiarities, imageUrls)
}