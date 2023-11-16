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

    init {
        _state.value = HotelInfoState.Initial
    }

    fun getHotelInfo() {
        viewModelScope.launch {
            _state.value = HotelInfoState.Loading
            getHotelInfoUseCase().collect {
                _state.value = HotelInfoState.Success(it)
            }
        }
    }

    fun openRoomInfo(hotelName: String) {
        router.openRoomInfo(hotelName)
    }
}