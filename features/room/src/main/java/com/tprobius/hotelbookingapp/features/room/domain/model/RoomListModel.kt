package com.tprobius.hotelbookingapp.features.room.domain.model

import com.tprobius.hotelbookingapp.utils.recyclerviewadapter.ListItem

data class RoomListModel(
    val rooms: List<RoomInfoModel?>?
) : ListItem