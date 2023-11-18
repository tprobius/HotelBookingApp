package com.tprobius.hotelbookingapp.features.hotel.domain.repository

import com.tprobius.hotelbookingapp.features.hotel.domain.model.HotelInfoModel
import kotlinx.coroutines.flow.Flow

interface HotelInfoApiRepository {
    fun getHotelInfo(): Flow<HotelInfoModel>
}