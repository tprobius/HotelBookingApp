package com.tprobius.hotelbookingapp.data.repository

import com.tprobius.hotelbookingapp.data.source.HotelBookingAppApi
import com.tprobius.hotelbookingapp.features.hotel.domain.model.HotelInfoModel
import com.tprobius.hotelbookingapp.features.hotel.domain.repository.HotelInfoApiRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class HotelInfoApiRepositoryImpl(
    private val hotelBookingAppApi: HotelBookingAppApi,
    private val dispatcher: CoroutineDispatcher
) : HotelInfoApiRepository {
    override fun getHotelInfo(): Flow<HotelInfoModel> {
        return flow {
            emit(hotelBookingAppApi.getHotelInfo().mapToHotelInfoModel())
        }.flowOn(dispatcher)
    }
}