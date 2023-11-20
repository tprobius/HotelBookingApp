package com.tprobius.hotelbookingapp.features.room.presentation.adapterdelegates

import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.tprobius.hotelbookingapp.utils.recyclerviewadapter.ListItem
import com.tprobius.hotelbookingapp.utils.recyclerviewadapter.ListItemDiffCallback

class RoomListAdapter(onClickListener: (ListItem) -> Unit) :
    AsyncListDifferDelegationAdapter<ListItem>(ListItemDiffCallback()) {
    init {
        delegatesManager.addDelegate(
            RoomListDelegates.roomInfoDelegate(onClickListener)
        ).addDelegate(RoomListDelegates.roomInfoDelegate(onClickListener))
    }
}