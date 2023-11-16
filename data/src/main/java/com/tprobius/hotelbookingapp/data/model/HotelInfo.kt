package com.tprobius.hotelbookingapp.data.model

import com.google.gson.annotations.SerializedName
import com.tprobius.hotelbookingapp.features.hotel.domain.model.HotelInfoModel

data class HotelInfo(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("adress")
    val address: String?,
    @SerializedName("minimal_price")
    val minimalPrice: Int?,
    @SerializedName("price_for_it")
    val priceForIt: String?,
    @SerializedName("rating")
    val rating: Int?,
    @SerializedName("rating_name")
    val ratingName: String?,
    @SerializedName("image_urls")
    val imageUrls: List<String?>?,
    @SerializedName("about_the_hotel")
    val aboutTheHotel: AboutTheHotel?
) {
    fun mapToHotelInfoModel() =
        HotelInfoModel(
            id,
            name,
            address,
            minimalPrice,
            priceForIt,
            rating,
            ratingName,
            imageUrls,
            aboutTheHotel?.toAboutTheHotelModel()
        )
}