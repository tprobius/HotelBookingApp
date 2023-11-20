package com.tprobius.hotelbookingapp.features.hotel.presentation.adapterdelegates

import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.tprobius.hotelbookingapp.utils.recyclerviewadapter.ListItem
import com.tprobius.hotelbookingapp.utils.recyclerviewadapter.ListItemDiffCallback

class HotelInfoAdapter : AsyncListDifferDelegationAdapter<ListItem>(ListItemDiffCallback()) {
    init {
        delegatesManager.addDelegate(HotelInfoDelegates.hotelInfoDelegate())
            .addDelegate(HotelInfoDelegates.hotelInfoDelegate())
        delegatesManager.addDelegate(HotelInfoDelegates.aboutTheHotelDelegate())
            .addDelegate(HotelInfoDelegates.aboutTheHotelDelegate())
    }
}