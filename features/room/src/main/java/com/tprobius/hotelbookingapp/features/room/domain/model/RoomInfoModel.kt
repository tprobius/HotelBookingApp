package com.tprobius.hotelbookingapp.features.room.domain.model

import com.tprobius.hotelbookingapp.utils.recyclerviewadapter.ListItem

data class RoomInfoModel(
    val id: Int?,
    val name: String?,
    val price: Int?,
    val pricePer: String?,
    val peculiarities: List<String?>?,
    val imageUrls: List<String?>?
) : ListItem