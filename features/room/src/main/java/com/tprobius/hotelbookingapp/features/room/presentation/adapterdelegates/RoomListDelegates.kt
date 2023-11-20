package com.tprobius.hotelbookingapp.features.room.presentation.adapterdelegates

import android.content.Context
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.chip.Chip
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import com.tprobius.hotelbookingapp.features.room.R
import com.tprobius.hotelbookingapp.features.room.databinding.ItemRoomInfoBinding
import com.tprobius.hotelbookingapp.features.room.domain.model.RoomInfoModel
import com.tprobius.hotelbookingapp.utils.recyclerviewadapter.ListItem
import com.tprobius.hotelbookingapp.utils.viewpageradapter.SliderAdapter

object RoomListDelegates {
    fun roomInfoDelegate(onClickListener: (RoomInfoModel) -> Unit) =
        adapterDelegateViewBinding<RoomInfoModel, ListItem, ItemRoomInfoBinding>(
            { layoutInflater, parent ->
                ItemRoomInfoBinding.inflate(layoutInflater, parent, false)
            }
        ) {
            val slider: ViewPager2 = binding.roomInfoViewPager
            val sliderAdapter = SliderAdapter()

            slider.adapter = sliderAdapter

            bind {
                binding.roomNameTextView.text = item.name
                binding.roomPriceTextView.text = getString(
                    R.string.price_string_format,
                    item.price ?: 0
                ).replace(',', ' ')
                binding.roomPriceCommentTextView.text = item.pricePer
                item.peculiarities?.forEach { tagName ->
                    binding.roomInfoChipGroup.addView(tagName?.let { createChip(context, it) })
                }
                binding.roomBookingButton.setOnClickListener {
                    onClickListener(item)
                }
                sliderAdapter.submitList(item.imageUrls)
            }
        }

    private fun createChip(context: Context?, chipName: String): Chip {
        val newChip = Chip(context, null, R.attr.CustomChip).apply {
            text = chipName
        }

        return newChip
    }
}