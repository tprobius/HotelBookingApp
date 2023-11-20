package com.tprobius.hotelbookingapp.features.payment.paymentinfofragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.tprobius.hotelbookingapp.features.payment.R
import com.tprobius.hotelbookingapp.features.payment.databinding.FragmentPaymentInfoBinding
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class PaymentInfoFragment : Fragment() {
    private val viewModel: PaymentInfoViewModel by viewModel()

    private var _binding: FragmentPaymentInfoBinding? = null
    private val binding
        get() = checkNotNull(_binding) { "Binding isn't initialized" }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                viewModel.onBackPress()
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(
            this,
            callback
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPaymentInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHandleState()
        setOnSuperClick()
    }

    private fun setHandleState() {
        viewModel.getBookingNumber()
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.state.collect {
                handleState(it)
            }
        }
    }

    private fun handleState(state: PaymentInfoState) {
        when (state) {
            PaymentInfoState.Initial -> showInitialState()
            PaymentInfoState.Loading -> showLoadingState()
            is PaymentInfoState.Success -> showSuccessState(state.bookingNumber)
            PaymentInfoState.Error -> showErrorState()
        }
    }

    private fun showInitialState() {
        setViewsVisibility()
    }

    private fun showLoadingState() {
        setViewsVisibility(hotelInfoProgressBar = true)
    }

    private fun showSuccessState(bookingNumber: String) {
        setViewsVisibility(
            paymentImageView = true,
            paymentCommentTextView = true,
            paymentMessageTextView = true,
            confirmPaymentButton = true
        )
        binding.paymentCommentTextView.text = getString(R.string.comment, bookingNumber)

    }

    private fun showErrorState() {
        setViewsVisibility(errorImageView = true, errorTextView = true, tryAgainButton = true)
    }

    private fun setViewsVisibility(
        paymentImageView: Boolean = false,
        paymentCommentTextView: Boolean = false,
        paymentMessageTextView: Boolean = false,
        hotelInfoProgressBar: Boolean = false,
        errorImageView: Boolean = false,
        errorTextView: Boolean = false,
        tryAgainButton: Boolean = false,
        confirmPaymentButton: Boolean = false
    ) {
        binding.paymentImageView.isVisible = paymentImageView
        binding.paymentCommentTextView.isVisible = paymentCommentTextView
        binding.paymentMessageTextView.isVisible = paymentMessageTextView
        binding.hotelInfoProgressBar.isVisible = hotelInfoProgressBar
        binding.errorImageView.isVisible = errorImageView
        binding.errorTextView.isVisible = errorTextView
        binding.tryAgainButton.isVisible = tryAgainButton
        binding.confirmPaymentButton.isEnabled = confirmPaymentButton
    }

    private fun setOnSuperClick() {
        binding.confirmPaymentButton.setOnClickListener {
            viewModel.confirmBooking()
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}