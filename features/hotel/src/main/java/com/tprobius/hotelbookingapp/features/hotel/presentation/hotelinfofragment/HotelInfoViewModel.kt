package com.tprobius.hotelbookingapp.features.hotel.presentation.hotelinfofragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tprobius.hotelbookingapp.features.hotel.domain.usecases.GetHotelInfoUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HotelInfoViewModel(
    private val getHotelInfoUseCase: GetHotelInfoUseCase,
    private val router: HotelInfoRouter
) : ViewModel() {
    private val _state: MutableStateFlow<HotelInfoState> = MutableStateFlow(HotelInfoState.Initial)
    val state: StateFlow<HotelInfoState> = _state

    fun getHotelInfo() {
        viewModelScope.launch {
            _state.value = HotelInfoState.Loading
            try {
                getHotelInfoUseCase().collect {
                    _state.value = HotelInfoState.Success(it)
                }
            } catch (e: Exception) {
                _state.value = HotelInfoState.Error
            }

        }
    }

    fun openRoomInfo(hotelName: String) {
        router.openRoomInfo(hotelName)
    }
}