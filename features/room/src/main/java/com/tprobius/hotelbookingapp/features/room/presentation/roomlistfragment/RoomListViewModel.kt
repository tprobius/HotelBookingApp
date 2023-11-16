package com.tprobius.hotelbookingapp.features.room.presentation.roomlistfragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tprobius.hotelbookingapp.features.room.domain.usecases.GetRoomListUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RoomListViewModel(
    private val getRoomListUseCase: GetRoomListUseCase,
    private val router: RoomListRouter,
    private val hotelName: String
) : ViewModel() {
    private val _state: MutableStateFlow<RoomListState> = MutableStateFlow(RoomListState.Initial)
    val state: StateFlow<RoomListState> = _state

    fun getRoomInfo() {
        viewModelScope.launch {
            _state.value = RoomListState.Loading
            try {
                getRoomListUseCase().collect {
                    _state.value = RoomListState.Success(it)
                }
            } catch (e: Exception) {
                _state.value = RoomListState.Error
            }
        }
    }

    fun openBookingInfo() {
        router.openBookingInfo()
    }

    fun backToHotelInfo() {
        router.backToHotelInfo()
    }

    fun getHotelName(): String = hotelName
}