package com.tprobius.hotelbookingapp.features.room.presentation.roomlistfragment

import com.github.terrakok.cicerone.androidx.FragmentScreen

fun getRoomInfoScreen(hotelName: String) =
    FragmentScreen { RoomListFragment.newInstance(hotelName) }