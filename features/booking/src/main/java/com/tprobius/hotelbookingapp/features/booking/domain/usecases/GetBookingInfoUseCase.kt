package com.tprobius.hotelbookingapp.features.booking.domain.usecases

import com.tprobius.hotelbookingapp.features.booking.domain.model.BookingInfoModel
import com.tprobius.hotelbookingapp.features.booking.domain.repository.BookingInfoApiRepository
import kotlinx.coroutines.flow.Flow

class GetBookingInfoUseCase(
    private val bookingInfoApiRepository: BookingInfoApiRepository
) {
    operator fun invoke(): Flow<BookingInfoModel> {
        return bookingInfoApiRepository.getBookingInfo()
    }
}