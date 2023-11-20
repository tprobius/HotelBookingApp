package com.tprobius.hotelbookingapp.features.hotel.presentation.adapterdelegates

import android.content.Context
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.chip.Chip
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import com.tprobius.hotelbookingapp.features.hotel.R
import com.tprobius.hotelbookingapp.features.hotel.databinding.ItemAboutTheHotelBinding
import com.tprobius.hotelbookingapp.features.hotel.databinding.ItemHotelInfoBinding
import com.tprobius.hotelbookingapp.features.hotel.domain.model.AboutTheHotelModel
import com.tprobius.hotelbookingapp.features.hotel.domain.model.HotelInfoModel
import com.tprobius.hotelbookingapp.utils.recyclerviewadapter.ListItem
import com.tprobius.hotelbookingapp.utils.viewpageradapter.SliderAdapter

object HotelInfoDelegates {
    fun hotelInfoDelegate() =
        adapterDelegateViewBinding<HotelInfoModel, ListItem, ItemHotelInfoBinding>(
            { layoutInflater, parent ->
                ItemHotelInfoBinding.inflate(layoutInflater, parent, false)
            }
        ) {
            val slider: ViewPager2 = binding.hotelInfoImagesViewPager
            val sliderAdapter = SliderAdapter()

            slider.adapter = sliderAdapter

            bind {
                sliderAdapter.submitList(item.imageUrls)
                binding.hotelRatingChip.text = getString(
                    R.string.rating_string_format,
                    item.rating ?: 0,
                    item.ratingName ?: ""
                )
                binding.hotelNameTextView.text = item.name
                binding.hotelAddressTextView.text = item.address
                binding.hotelPriceTextView.text = getString(
                    R.string.minimal_price_string_format,
                    item.minimalPrice ?: 0
                ).replace(',', ' ')
                binding.priceCommentTextView.text = item.priceForIt
            }
        }

    fun aboutTheHotelDelegate() =
        adapterDelegateViewBinding<AboutTheHotelModel, ListItem, ItemAboutTheHotelBinding>(
            { layoutInflater, parent ->
                ItemAboutTheHotelBinding.inflate(layoutInflater, parent, false)
            }
        ) {
            bind {
                binding.hotelDescriptionTextView.text = item.description
                item.peculiarities?.forEach { tagName ->
                    binding.peculiaritiesChipGroup.addView(tagName?.let { createChip(context, it) })
                }
            }
        }

    private fun createChip(context: Context?, chipName: String): Chip {
        return Chip(context, null, R.attr.CustomChip).apply {
            text = chipName
        }
    }
}