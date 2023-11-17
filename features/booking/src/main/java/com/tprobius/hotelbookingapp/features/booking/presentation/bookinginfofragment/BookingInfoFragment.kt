package com.tprobius.hotelbookingapp.features.booking.presentation.bookinginfofragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import com.tprobius.hotelbookingapp.features.booking.databinding.FragmentBookingInfoBinding
import com.tprobius.hotelbookingapp.features.booking.domain.model.BookingInfoModel
import com.tprobius.hotelbookingapp.features.booking.domain.model.CostInfoModel
import com.tprobius.hotelbookingapp.features.booking.domain.model.CustomerInfoModel
import com.tprobius.hotelbookingapp.features.booking.domain.model.HotelInfoModel
import com.tprobius.hotelbookingapp.features.booking.domain.model.NewTouristModel
import com.tprobius.hotelbookingapp.features.booking.domain.model.TourInfoModel
import com.tprobius.hotelbookingapp.features.booking.domain.model.TouristInfoModel
import com.tprobius.hotelbookingapp.features.booking.presentation.adapterdelegates.BookingInfoDelegates
import com.tprobius.hotelbookingapp.utils.recyclerviewadapter.ListItem
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class BookingInfoFragment : Fragment() {
    private val viewModel: BookingInfoViewModel by viewModel()

    private var _binding: FragmentBookingInfoBinding? = null
    private val binding
        get() = checkNotNull(_binding) { "Binding isn't initialized" }

    private lateinit var bookingAdapter: ListDelegationAdapter<List<ListItem>>
    private var listOfItems: MutableList<ListItem> = mutableListOf()

    private var touristCount = 1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBookingInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHandleState()
        setBookingAdapter()
        setOnPayButton()
    }

    private fun setHandleState() {
        viewModel.getBookingInfo()
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.state.collect {
                handleState(it)
            }
        }
    }

    private fun handleState(state: BookingInfoState) {
        when (state) {
            BookingInfoState.Initial -> showInitialState()
            BookingInfoState.Loading -> showLoadingState()
            is BookingInfoState.Success -> showSuccessState(state.bookingInfo)
            BookingInfoState.Error -> showErrorState()
        }
    }

    private fun setBookingAdapter() {
        bookingAdapter = ListDelegationAdapter(
            BookingInfoDelegates.hotelBookingInfoDelegate(),
            BookingInfoDelegates.tourInfoDelegates(),
            BookingInfoDelegates.customerInfoDelegates(),
            BookingInfoDelegates.touristInfoDelegate(),
            BookingInfoDelegates.newTouristDelegate {
                addNewTourist()
            },
            BookingInfoDelegates.costInfoDelegate()
        )
        binding.bookingInfoRecyclerView.adapter = bookingAdapter
    }

    private fun showInitialState() {
        binding.topAppBarLinearLayout.isVisible = true
        binding.bookingInfoRecyclerView.isVisible = false
        binding.hotelInfoProgressBar.isVisible = false
        binding.errorImageView.isVisible = false
        binding.errorTextView.isVisible = false
        binding.tryAgainButton.isVisible = false
        binding.topAppBarLinearLayout.isVisible = true
        binding.payBookingButton.text = ""
        binding.payBookingButton.isEnabled = false
    }

    private fun showLoadingState() {
        binding.topAppBarLinearLayout.isVisible = true
        binding.bookingInfoRecyclerView.isVisible = false
        binding.hotelInfoProgressBar.isVisible = true
        binding.errorImageView.isVisible = false
        binding.errorTextView.isVisible = false
        binding.tryAgainButton.isVisible = false
        binding.topAppBarLinearLayout.isVisible = true
        binding.payBookingButton.text = ""
        binding.payBookingButton.isEnabled = false
    }

    private fun showSuccessState(bookingInfo: BookingInfoModel) {
        binding.payBookingButton.text =
            "Оплатить " + ((bookingInfo.tourPrice ?: 0) + (bookingInfo.fuelCharge
                ?: 0) + (bookingInfo.serviceCharge ?: 0)).toString() + "₽"
        binding.topAppBarLinearLayout.isVisible = true
        binding.bookingInfoRecyclerView.isVisible = true
        binding.hotelInfoProgressBar.isVisible = false
        binding.errorImageView.isVisible = false
        binding.errorTextView.isVisible = false
        binding.tryAgainButton.isVisible = false
        binding.topAppBarLinearLayout.isVisible = true
        binding.payBookingButton.isEnabled = true

        listOfItems = roomInfoToRoomInfoItem(bookingInfo)
        viewLifecycleOwner.lifecycleScope.launch {
            bookingAdapter.apply {
                items = listOfItems
                notifyDataSetChanged()
            }
        }
        setOnBackClick(bookingInfo)
    }

    private fun roomInfoToRoomInfoItem(bookingInfo: BookingInfoModel): MutableList<ListItem> {
        listOfItems = mutableListOf(
            HotelInfoModel(
                bookingInfo.hotelRating,
                bookingInfo.hotelAddress,
                bookingInfo.hotelName,
                bookingInfo.ratingName
            ),
            TourInfoModel(
                bookingInfo.arrivalCountry,
                bookingInfo.departure,
                bookingInfo.hotelName,
                bookingInfo.numberOfNights,
                bookingInfo.nutrition,
                bookingInfo.room,
                bookingInfo.tourDateStart,
                bookingInfo.tourDateStop
            ),
            CustomerInfoModel(),
            TouristInfoModel(),
            NewTouristModel(),
            CostInfoModel(
                bookingInfo.fuelCharge,
                bookingInfo.serviceCharge,
                bookingInfo.tourPrice
            )
        )
        return listOfItems
    }

    private fun addNewTourist() {
        val position = listOfItems.size - 2
        listOfItems.add(position, TouristInfoModel(touristCount = touristOrder()))
        touristCount++
        viewLifecycleOwner.lifecycleScope.launch {
            bookingAdapter.apply {
                items = listOfItems
                notifyDataSetChanged()
            }
        }
    }

    private fun touristOrder() =
        when (touristCount) {
            1 -> "Второй"
            2 -> "Третий"
            3 -> "Чертвертый"
            4 -> "Пятый"
            5 -> "Шестой"
            6 -> "Седьмой"
            7 -> "Восьмой"
            8 -> "Девятый"
            else -> "Десятый"
        }

    private fun showErrorState() {
        binding.topAppBarLinearLayout.isVisible = true
        binding.bookingInfoRecyclerView.isVisible = false
        binding.hotelInfoProgressBar.isVisible = false
        binding.errorImageView.isVisible = true
        binding.errorTextView.isVisible = true
        binding.tryAgainButton.isVisible = true
        binding.topAppBarLinearLayout.isVisible = true
        binding.payBookingButton.isEnabled = false
    }

    private fun setOnPayButton() {
        binding.payBookingButton.setOnClickListener {
            viewModel.openPaymentInfo()
        }
    }

    private fun setOnBackClick(bookingInfo: BookingInfoModel) {
        binding.backImageView.setOnClickListener {
            bookingInfo.hotelName?.let { hotelName -> viewModel.backToRoomInfo(hotelName) }
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}