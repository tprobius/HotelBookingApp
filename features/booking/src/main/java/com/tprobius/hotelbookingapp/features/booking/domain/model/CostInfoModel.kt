package com.tprobius.hotelbookingapp.features.booking.domain.model

import com.tprobius.hotelbookingapp.utils.recyclerviewadapter.ListItem

data class CostInfoModel(
    val fuelCharge: Int?,
    val serviceCharge: Int?,
    val tourPrice: Int?
) : ListItem