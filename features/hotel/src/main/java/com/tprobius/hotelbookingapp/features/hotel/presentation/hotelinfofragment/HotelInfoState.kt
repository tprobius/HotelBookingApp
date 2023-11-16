package com.tprobius.hotelbookingapp.features.hotel.presentation.hotelinfofragment

import com.tprobius.hotelbookingapp.features.hotel.domain.model.HotelInfoModel

sealed interface HotelInfoState {
    object Initial : HotelInfoState
    object Loading : HotelInfoState
    data class Success(val hotelInfo: HotelInfoModel) : HotelInfoState
    object Error : HotelInfoState
}