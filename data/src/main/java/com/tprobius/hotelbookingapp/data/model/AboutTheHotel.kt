package com.tprobius.hotelbookingapp.data.model


import com.google.gson.annotations.SerializedName
import com.tprobius.hotelbookingapp.features.hotel.domain.model.AboutTheHotelModel

data class AboutTheHotel(
    @SerializedName("description")
    val description: String?,
    @SerializedName("peculiarities")
    val peculiarities: List<String?>?
) {
    fun toAboutTheHotelModel() = AboutTheHotelModel(description, peculiarities)
}