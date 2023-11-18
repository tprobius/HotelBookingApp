package com.tprobius.hotelbookingapp.features.payment.paymentinfofragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
        setOnBackClick()
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
        binding.topAppBarLinearLayout.isVisible = true
        binding.paymentImageView.isVisible = false
        binding.paymentCommentTextView.isVisible = false
        binding.paymentMessageTextView.isVisible = false
        binding.hotelInfoProgressBar.isVisible = false
        binding.errorImageView.isVisible = false
        binding.errorTextView.isVisible = false
        binding.tryAgainButton.isVisible = false
        binding.bottomAppBarLinearLayout.isVisible = true
        binding.bottomAppBarLinearLayout.isEnabled = false
    }

    private fun showLoadingState() {
        binding.topAppBarLinearLayout.isVisible = true
        binding.paymentImageView.isVisible = false
        binding.paymentCommentTextView.isVisible = false
        binding.paymentMessageTextView.isVisible = false
        binding.hotelInfoProgressBar.isVisible = true
        binding.errorImageView.isVisible = false
        binding.errorTextView.isVisible = false
        binding.tryAgainButton.isVisible = false
        binding.bottomAppBarLinearLayout.isVisible = true
        binding.bottomAppBarLinearLayout.isEnabled = false
    }

    private fun showSuccessState(bookingNumber: String) {
        binding.topAppBarLinearLayout.isVisible = true
        binding.paymentImageView.isVisible = true
        binding.paymentCommentTextView.isVisible = true
        binding.paymentMessageTextView.isVisible = true
        binding.hotelInfoProgressBar.isVisible = false
        binding.errorImageView.isVisible = false
        binding.errorTextView.isVisible = false
        binding.tryAgainButton.isVisible = false
        binding.bottomAppBarLinearLayout.isVisible = true
        binding.bottomAppBarLinearLayout.isEnabled = true
        binding.paymentCommentTextView.text = getString(R.string.comment, bookingNumber)
    }

    private fun showErrorState() {
        binding.topAppBarLinearLayout.isVisible = true
        binding.paymentImageView.isVisible = false
        binding.paymentCommentTextView.isVisible = false
        binding.paymentMessageTextView.isVisible = false
        binding.hotelInfoProgressBar.isVisible = false
        binding.errorImageView.isVisible = true
        binding.errorTextView.isVisible = true
        binding.tryAgainButton.isVisible = true
        binding.bottomAppBarLinearLayout.isVisible = true
        binding.bottomAppBarLinearLayout.isEnabled = false
    }

    private fun setOnBackClick() {
        binding.backImageView.setOnClickListener {
            viewModel.backToBookingInfo()
        }
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