package com.tprobius.hotelbookingapp.features.booking.presentation.adapterdelegates

import android.util.Patterns
import android.widget.EditText
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.hannesdorfmann.adapterdelegates4.dsl.AdapterDelegateViewBindingViewHolder
import com.tprobius.hotelbookingapp.features.booking.R
import com.tprobius.hotelbookingapp.features.booking.databinding.ItemTouristInfoBinding
import com.tprobius.hotelbookingapp.features.booking.domain.model.TouristInfoModel
import java.text.SimpleDateFormat
import java.util.Locale

private const val PHONE_NUMBER_LENGTH = 18

fun EditText.setImeActionPhone(cardView: CardView) {
    setOnEditorActionListener { _, _, _ ->
        if (text.length < PHONE_NUMBER_LENGTH) {
            cardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.rose_light))
            true
        } else {
            cardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.gray_light))
            false
        }
    }
}

fun EditText.onFocusChangePhone(cardView: CardView) {
    setOnFocusChangeListener { _, _ ->
        if (hasFocus()) {
            if (text.isNullOrEmpty()) {
                cardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.gray_light))
            }
        } else {
            if (text.isNullOrEmpty() || text.length < PHONE_NUMBER_LENGTH) {
                cardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.rose_light))
            } else {
                cardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.gray_light))
            }
        }
    }
}

fun EditText.setImeActionEmail(cardView: CardView) {
    setOnEditorActionListener { _, _, _ ->
        if (!text.isValidEmail()) {
            cardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.rose_light))
            true
        } else {
            cardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.gray_light))
            false
        }
    }
}

fun CharSequence?.isValidEmail() =
    !isNullOrEmpty() && Patterns.EMAIL_ADDRESS.matcher(this).matches()

fun EditText.onFocusChangeEmail(cardView: CardView) {
    setOnFocusChangeListener { _, _ ->
        if (hasFocus()) {
            if (text.isNullOrEmpty()) {
                cardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.gray_light))
            }
        } else {
            if (text.isNullOrEmpty() || !text.isValidEmail()) {
                cardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.rose_light))
            } else {
                cardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.gray_light))
            }
        }
    }
}

fun EditText.setImeAction(cardView: CardView) {
    setOnEditorActionListener { _, _, _ ->
        if (text.isNullOrEmpty()) {
            cardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.rose_light))
            true
        } else {
            cardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.gray_light))
            false
        }
    }
}

fun EditText.onFocusChange(cardView: CardView) {
    setOnFocusChangeListener { _, _ ->
        if (hasFocus()) {
            if (text.isNullOrEmpty()) {
                cardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.gray_light))
            }
        } else {
            if (text.isNullOrEmpty()) {
                cardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.rose_light))
            } else {
                cardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.gray_light))
            }
        }
    }
}

fun EditText.setImeActionDate(cardView: CardView) {
    setOnEditorActionListener { _, _, _ ->
        val date = text.toString()
        if (!validateDate(date)) {
            cardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.rose_light))
            true
        } else {
            cardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.gray_light))
            false
        }
    }
}

fun EditText.onFocusChangeDate(cardView: CardView) {
    setOnFocusChangeListener { _, _ ->
        val date = text.toString()
        if (hasFocus()) {
            if (text.isNullOrEmpty()) {
                cardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.gray_light))
            }
        } else {
            if (text.isNullOrEmpty() || !validateDate(date)) {
                cardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.rose_light))
            } else {
                cardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.gray_light))
            }
        }
    }
}

fun validateDate(date: String): Boolean {
    val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
    dateFormat.isLenient = false
    return try {
        dateFormat.parse(date)
        true
    } catch (e: Exception) {
        false
    }
}

fun AdapterDelegateViewBindingViewHolder<TouristInfoModel, ItemTouristInfoBinding>.showHideInfo(
    isOpen: Boolean
): Boolean {
    return when (isOpen) {
        false -> {
            binding.firstNameCardView.isVisible = true
            binding.lastNameCardView.isVisible = true
            binding.dateOfBirthCardView.isVisible = true
            binding.citizenshipCardView.isVisible = true
            binding.passportCardView.isVisible = true
            binding.passportExpirationDateCardView.isVisible = true
            binding.arrowImageView.setImageResource(R.drawable.ic_arrow_up)
            true
        }

        else -> {
            binding.firstNameCardView.isVisible = false
            binding.lastNameCardView.isVisible = false
            binding.dateOfBirthCardView.isVisible = false
            binding.citizenshipCardView.isVisible = false
            binding.passportCardView.isVisible = false
            binding.passportExpirationDateCardView.isVisible = false
            binding.arrowImageView.setImageResource(R.drawable.ic_arrow_down)
            false
        }
    }
}