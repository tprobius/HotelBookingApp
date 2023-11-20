package com.tprobius.hotelbookingapp.features.booking.presentation.adapterdelegates

import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import com.tprobius.hotelbookingapp.features.booking.R
import com.tprobius.hotelbookingapp.features.booking.databinding.ItemCostInfoBinding
import com.tprobius.hotelbookingapp.features.booking.databinding.ItemCustomerInfoBinding
import com.tprobius.hotelbookingapp.features.booking.databinding.ItemHotelBookingInfoBinding
import com.tprobius.hotelbookingapp.features.booking.databinding.ItemNewTouristBinding
import com.tprobius.hotelbookingapp.features.booking.databinding.ItemTourInfoBinding
import com.tprobius.hotelbookingapp.features.booking.databinding.ItemTouristInfoBinding
import com.tprobius.hotelbookingapp.features.booking.domain.model.CostInfoModel
import com.tprobius.hotelbookingapp.features.booking.domain.model.CustomerInfoModel
import com.tprobius.hotelbookingapp.features.booking.domain.model.HotelInfoModel
import com.tprobius.hotelbookingapp.features.booking.domain.model.NewTouristModel
import com.tprobius.hotelbookingapp.features.booking.domain.model.TourInfoModel
import com.tprobius.hotelbookingapp.features.booking.domain.model.TouristInfoModel
import com.tprobius.hotelbookingapp.utils.recyclerviewadapter.ListItem
import ru.tinkoff.decoro.MaskImpl
import ru.tinkoff.decoro.slots.PredefinedSlots
import ru.tinkoff.decoro.slots.Slot
import ru.tinkoff.decoro.watchers.FormatWatcher
import ru.tinkoff.decoro.watchers.MaskFormatWatcher

object BookingInfoDelegates {
    fun hotelBookingInfoDelegate() =
        adapterDelegateViewBinding<HotelInfoModel, ListItem, ItemHotelBookingInfoBinding>(
            { layoutInflater, parent ->
                ItemHotelBookingInfoBinding.inflate(layoutInflater, parent, false)
            }
        ) {
            bind {
                binding.hotelRatingChip.text = getString(
                    R.string.rating_string_format,
                    item.hotelRating ?: 0,
                    item.ratingName ?: ""
                )
                binding.hotelNameTextView.text = item.hotelName
                binding.hotelAddressTextView.text = item.hotelAddress
            }
        }

    fun tourInfoDelegates() =
        adapterDelegateViewBinding<TourInfoModel, ListItem, ItemTourInfoBinding>(
            { layoutInflater, parent ->
                ItemTourInfoBinding.inflate(layoutInflater, parent, false)
            }
        ) {
            bind {
                binding.departureValueTextView.text = item.departure
                binding.arrivalValueTextView.text = item.arrivalCountry
                binding.datesValueTextView.text = getString(
                    R.string.dates_string_format,
                    item.tourDateStart ?: 0,
                    item.tourDateStop ?: 0
                )
                binding.nightsValueTextView.text = item.numberOfNights.toString()
                binding.hotelValueTextView.text = item.hotelName
                binding.roomValueTextView.text = item.room
                binding.nutritionValueTextView.text = item.nutrition
            }
        }

    fun customerInfoDelegates() =
        adapterDelegateViewBinding<CustomerInfoModel, ListItem, ItemCustomerInfoBinding>(
            { layoutInflater, parent ->
                ItemCustomerInfoBinding.inflate(layoutInflater, parent, false)
            }
        ) {
            val formatWatcher: FormatWatcher = MaskFormatWatcher(
                MaskImpl.createTerminated(PredefinedSlots.RUS_PHONE_NUMBER)
            )

            bind {
                formatWatcher.installOn(binding.phoneNumberEditText)

                binding.phoneNumberEditText.setImeActionPhone(binding.phoneNumberCardView)
                binding.phoneNumberEditText.onFocusChangePhone(binding.phoneNumberCardView)

                binding.emailEditText.setImeActionEmail(binding.emailCardView)
                binding.emailEditText.onFocusChangeEmail(binding.emailCardView)
            }
        }

    fun touristInfoDelegate() =
        adapterDelegateViewBinding<TouristInfoModel, ListItem, ItemTouristInfoBinding>(
            { layoutInflater, parent ->
                ItemTouristInfoBinding.inflate(layoutInflater, parent, false)
            }
        ) {
            val internationalPassportFormat = arrayOf(
                PredefinedSlots.digit(),
                PredefinedSlots.digit(),
                PredefinedSlots.hardcodedSlot(' ').withTags(Slot.TAG_DECORATION),
                PredefinedSlots.digit(),
                PredefinedSlots.digit(),
                PredefinedSlots.digit(),
                PredefinedSlots.digit(),
                PredefinedSlots.digit(),
                PredefinedSlots.digit(),
                PredefinedSlots.digit()
            )

            val dateFormat = arrayOf(
                PredefinedSlots.digit(),
                PredefinedSlots.digit(),
                PredefinedSlots.hardcodedSlot('.').withTags(Slot.TAG_DECORATION),
                PredefinedSlots.digit(),
                PredefinedSlots.digit(),
                PredefinedSlots.hardcodedSlot('.').withTags(Slot.TAG_DECORATION),
                PredefinedSlots.digit(),
                PredefinedSlots.digit(),
                PredefinedSlots.digit(),
                PredefinedSlots.digit()
            )

            val internationalPassportFormatWatcher: FormatWatcher = MaskFormatWatcher(
                MaskImpl.createTerminated(internationalPassportFormat)
            )
            val dateOfBirthFormatWatcher: FormatWatcher = MaskFormatWatcher(
                MaskImpl.createTerminated(dateFormat)
            )
            val dateOfPassportFormatWatcher: FormatWatcher = MaskFormatWatcher(
                MaskImpl.createTerminated(dateFormat)
            )

            bind {
                var isOpen = true

                binding.titleTextView.text = getString(
                    R.string.tourist_count_string_format,
                    item.touristCount ?: 1
                )

                internationalPassportFormatWatcher.installOn(binding.passportEditText)
                dateOfBirthFormatWatcher.installOn(binding.dateOfBirthEditText)
                dateOfPassportFormatWatcher.installOn(binding.passportExpirationDateEditText)

                binding.titleTextView.setOnClickListener { isOpen = showHideInfo(isOpen) }
                binding.arrowImageView.setOnClickListener { isOpen = showHideInfo(isOpen) }

                binding.firstNameEditText.setImeAction(binding.firstNameCardView)
                binding.firstNameEditText.onFocusChange(binding.firstNameCardView)

                binding.lastNameEditText.setImeAction(binding.lastNameCardView)
                binding.lastNameEditText.onFocusChange(binding.lastNameCardView)

                binding.dateOfBirthEditText.setImeActionDate(binding.dateOfBirthCardView)
                binding.dateOfBirthEditText.onFocusChangeDate(binding.dateOfBirthCardView)

                binding.citizenshipEditText.setImeAction(binding.citizenshipCardView)
                binding.citizenshipEditText.onFocusChange(binding.citizenshipCardView)

                binding.passportEditText.setImeAction(binding.passportCardView)
                binding.passportEditText.onFocusChange(binding.passportCardView)

                binding.passportExpirationDateEditText.setImeActionDate(binding.passportExpirationDateCardView)
                binding.passportExpirationDateEditText.onFocusChangeDate(binding.passportExpirationDateCardView)
            }
        }


    fun newTouristDelegate(onAddClick: () -> Unit) =
        adapterDelegateViewBinding<NewTouristModel, ListItem, ItemNewTouristBinding>(
            { layoutInflater, parent ->
                ItemNewTouristBinding.inflate(layoutInflater, parent, false)
            }
        ) {
            bind {
                binding.newTouristCardView.setOnClickListener {
                    onAddClick()
                }
            }
        }

    fun costInfoDelegate() =
        adapterDelegateViewBinding<CostInfoModel, ListItem, ItemCostInfoBinding>(
            { layoutInflater, parent ->
                ItemCostInfoBinding.inflate(layoutInflater, parent, false)
            }
        ) {
            bind {
                binding.tourPriceValueTextView.text = getString(
                    R.string.price_string_format,
                    item.tourPrice ?: 0
                ).replace(',', ' ')
                binding.fuelChargeValueTextView.text = getString(
                    R.string.price_string_format,
                    item.fuelCharge ?: 0
                ).replace(',', ' ')
                binding.serviceChargeValueTextView.text = getString(
                    R.string.price_string_format,
                    item.serviceCharge ?: 0
                ).replace(',', ' ')
                binding.totalPriceValueTextView.text = getString(
                    R.string.price_string_format,
                    (item.tourPrice ?: 0)
                        .plus(item.fuelCharge ?: 0)
                        .plus(item.serviceCharge ?: 0)
                ).replace(',', ' ')
            }
        }
}