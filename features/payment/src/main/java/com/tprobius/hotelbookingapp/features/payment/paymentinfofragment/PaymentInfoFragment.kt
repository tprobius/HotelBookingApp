package com.tprobius.hotelbookingapp.features.payment.paymentinfofragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
//        TODO("Not yet implemented")
    }

    private fun showLoadingState() {
//        TODO("Not yet implemented")
    }

    private fun showSuccessState(bookingNumber: String) {
        binding.paymentCommentTextView.text = getString(R.string.comment, bookingNumber)
    }

    private fun showErrorState() {
//        TODO("Not yet implemented")
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