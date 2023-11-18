package com.tprobius.hotelbookingapp.features.hotel.domain.usecases

import com.tprobius.hotelbookingapp.features.hotel.domain.model.HotelInfoModel
import com.tprobius.hotelbookingapp.features.hotel.domain.repository.HotelInfoApiRepository
import kotlinx.coroutines.flow.Flow

class GetHotelInfoUseCase(
    private val hotelInfoApiRepository: HotelInfoApiRepository
) {
    operator fun invoke(): Flow<HotelInfoModel> {
        return hotelInfoApiRepository.getHotelInfo()
    }
}