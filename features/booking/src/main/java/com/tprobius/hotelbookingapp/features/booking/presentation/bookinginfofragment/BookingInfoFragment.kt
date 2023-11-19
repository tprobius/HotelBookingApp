package com.tprobius.hotelbookingapp.features.booking.presentation.bookinginfofragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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
        setTryAgainButton()
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
            BookingInfoDelegates.newTouristDelegate { addNewTourist() },
            BookingInfoDelegates.costInfoDelegate()
        )
        binding.bookingInfoRecyclerView.adapter = bookingAdapter
    }

    private fun showInitialState() {
        setViewsVisibility()
    }

    private fun showLoadingState() {
        setViewsVisibility(hotelInfoProgressBar = true)
    }


    private fun showSuccessState(bookingInfo: BookingInfoModel) {
        setViewsVisibility(
            bookingInfoRecyclerView = true,
            payBookingButtonText = "Оплатить " + ((bookingInfo.tourPrice
                ?: 0) + (bookingInfo.fuelCharge
                ?: 0) + (bookingInfo.serviceCharge ?: 0)).toString() + "₽",
            payBookingButton = true
        )

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
        setViewsVisibility(
            errorImageView = true,
            errorTextView = true,
            tryAgainButton = true
        )
    }


    private fun setViewsVisibility(
        bookingInfoRecyclerView: Boolean = false,
        hotelInfoProgressBar: Boolean = false,
        errorImageView: Boolean = false,
        errorTextView: Boolean = false,
        tryAgainButton: Boolean = false,
        payBookingButtonText: String = "Оплатить",
        payBookingButton: Boolean = false
    ) {
        binding.bookingInfoRecyclerView.isVisible = bookingInfoRecyclerView
        binding.hotelInfoProgressBar.isVisible = hotelInfoProgressBar
        binding.errorImageView.isVisible = errorImageView
        binding.errorTextView.isVisible = errorTextView
        binding.tryAgainButton.isVisible = tryAgainButton
        binding.payBookingButton.text = payBookingButtonText
        binding.payBookingButton.isEnabled = payBookingButton
    }

    private fun setTryAgainButton() {
        binding.tryAgainButton.setOnClickListener {
            viewModel.getBookingInfo()
        }
    }

    private fun setOnPayButton() {
        var isValid = false

        binding.payBookingButton.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                BookingInfoDelegates.isValid().collect {
                    isValid = it
                    Log.d("WTF?!?!?!?!?!", "$isValid")
                }
            }
            if (isValid) {
                viewModel.openPaymentInfo()
            } else {
                Toast.makeText(requireContext(), "Проверьте корректность введённых данных.", Toast.LENGTH_LONG).show()
            }
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