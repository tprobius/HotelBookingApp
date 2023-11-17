package com.tprobius.hotelbookingapp.features.booking.domain.model

import com.tprobius.hotelbookingapp.utils.recyclerviewadapter.ListItem

data class CustomerInfoModel(
    val phoneNumber: String? = "",
    val email: String? = ""
) : ListItem
