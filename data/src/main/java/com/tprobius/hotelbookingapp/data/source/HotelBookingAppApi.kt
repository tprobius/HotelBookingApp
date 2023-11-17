package com.tprobius.hotelbookingapp.data.source


import com.tprobius.hotelbookingapp.data.model.BookingInfo
import com.tprobius.hotelbookingapp.data.model.HotelInfo
import com.tprobius.hotelbookingapp.data.model.RoomList
import retrofit2.http.GET

interface HotelBookingAppApi {
    @GET("d144777c-a67f-4e35-867a-cacc3b827473/")
    suspend fun getHotelInfo(): HotelInfo

    @GET("8b532701-709e-4194-a41c-1a903af00195/")
    suspend fun getRoomList(): RoomList

    @GET("63866c74-d593-432c-af8e-f279d1a8d2ff/")
    suspend fun getBookingInfo(): BookingInfo

    companion object {
        const val BASE_URL = "https://run.mocky.io/v3/"
    }
}