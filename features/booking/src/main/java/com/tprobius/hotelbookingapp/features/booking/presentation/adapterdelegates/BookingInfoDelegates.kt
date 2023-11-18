package com.tprobius.hotelbookingapp.features.booking.presentation.adapterdelegates

import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
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
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.tinkoff.decoro.MaskImpl
import ru.tinkoff.decoro.slots.PredefinedSlots
import ru.tinkoff.decoro.slots.Slot
import ru.tinkoff.decoro.watchers.FormatWatcher
import ru.tinkoff.decoro.watchers.MaskFormatWatcher

object Validation {
    var phoneNumberIsValid = false
//    var emailAddressIsValid = false
//    var firstNameIsValid = false
//    var lastNameIsValid = false
//    var dateOfBirthIsValid = false
//    var citizenshipIsValid = false
//    var passportNumberIsValid = false
//    var passportExpirationDateIsValid = false
}

object BookingInfoDelegates {
    fun isValid(): Flow<Boolean> = flow { emit(Validation.phoneNumberIsValid) }
//            &&
//            Validation.emailAddressIsValid &&
//            Validation.firstNameIsValid &&
//            Validation.lastNameIsValid &&
//            Validation.dateOfBirthIsValid &&
//            Validation.citizenshipIsValid &&
//            Validation.passportNumberIsValid &&
//            Validation.passportExpirationDateIsValid

    fun hotelBookingInfoDelegate() =
        adapterDelegateViewBinding<HotelInfoModel, ListItem, ItemHotelBookingInfoBinding>(
            { layoutInflater, parent ->
                ItemHotelBookingInfoBinding.inflate(layoutInflater, parent, false)
            }
        ) {
            bind {
                binding.hotelRatingChip.text = "${item.hotelRating} ${item.ratingName}"
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
                binding.datesValueTextView.text = "${item.tourDateStart} - ${item.tourDateStop}"
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

                binding.titleTextView.text = "${item.touristCount} турист"

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
                binding.tourPriceValueTextView.text = "${item.tourPrice} ₽"
                binding.fuelChargeValueTextView.text = "${item.fuelCharge} ₽"
                binding.serviceChargeValueTextView.text = "${item.serviceCharge} ₽"
                binding.totalPriceValueTextView.text = " ${
                    (item.tourPrice ?: 0).plus(item.fuelCharge ?: 0).plus(item.serviceCharge ?: 0)
                } ₽"
            }
        }
}