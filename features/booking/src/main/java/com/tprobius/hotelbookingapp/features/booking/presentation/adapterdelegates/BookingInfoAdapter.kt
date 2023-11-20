package com.tprobius.hotelbookingapp.features.booking.presentation.adapterdelegates

import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.tprobius.hotelbookingapp.utils.recyclerviewadapter.ListItem
import com.tprobius.hotelbookingapp.utils.recyclerviewadapter.ListItemDiffCallback

class BookingInfoAdapter(onClickListener: () -> Unit) :
    AsyncListDifferDelegationAdapter<ListItem>(ListItemDiffCallback()) {
    init {
        delegatesManager.addDelegate(BookingInfoDelegates.hotelBookingInfoDelegate())
            .addDelegate(BookingInfoDelegates.hotelBookingInfoDelegate())
        delegatesManager.addDelegate(BookingInfoDelegates.tourInfoDelegates())
            .addDelegate(BookingInfoDelegates.tourInfoDelegates())
        delegatesManager.addDelegate(BookingInfoDelegates.customerInfoDelegates())
            .addDelegate(BookingInfoDelegates.customerInfoDelegates())
        delegatesManager.addDelegate(BookingInfoDelegates.touristInfoDelegate())
            .addDelegate(BookingInfoDelegates.touristInfoDelegate())
        delegatesManager.addDelegate(BookingInfoDelegates.newTouristDelegate(onClickListener))
            .addDelegate(BookingInfoDelegates.newTouristDelegate(onClickListener))
        delegatesManager.addDelegate(BookingInfoDelegates.costInfoDelegate())
            .addDelegate(BookingInfoDelegates.costInfoDelegate())
    }
}