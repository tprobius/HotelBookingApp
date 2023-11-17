package com.tprobius.hotelbookingapp.data.repository

import com.tprobius.hotelbookingapp.data.source.HotelBookingAppApi
import com.tprobius.hotelbookingapp.features.booking.domain.model.BookingInfoModel
import com.tprobius.hotelbookingapp.features.booking.domain.repository.BookingInfoApiRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class BookingInfoApiRepositoryImpl(
    private val hotelBookingAppApi: HotelBookingAppApi,
    private val dispatcher: CoroutineDispatcher
) : BookingInfoApiRepository {
    override fun getBookingInfo(): Flow<BookingInfoModel> {
        return flow {
            emit(hotelBookingAppApi.getBookingInfo().mapToBookingInfoModel())
        }.flowOn(dispatcher)
    }
}