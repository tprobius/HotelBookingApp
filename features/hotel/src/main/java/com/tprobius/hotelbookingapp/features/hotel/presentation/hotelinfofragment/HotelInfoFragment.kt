package com.tprobius.hotelbookingapp.features.hotel.presentation.hotelinfofragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.tprobius.hotelbookingapp.features.hotel.databinding.FragmentHotelInfoBinding
import com.tprobius.hotelbookingapp.features.hotel.domain.model.AboutTheHotelModel
import com.tprobius.hotelbookingapp.features.hotel.domain.model.HotelInfoModel
import com.tprobius.hotelbookingapp.features.hotel.presentation.adapterdelegates.HotelInfoAdapter
import com.tprobius.hotelbookingapp.utils.recyclerviewadapter.ListItem
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class HotelInfoFragment : Fragment() {
    private val viewModel: HotelInfoViewModel by viewModel()

    private var _binding: FragmentHotelInfoBinding? = null
    private val binding
        get() = checkNotNull(_binding) { "Binding isn't initialized" }

    private lateinit var hotelInfoAdapter: HotelInfoAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHotelInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHandleState()
        setHotelAdapter()
        setTryAgainButton()
    }

    private fun setHandleState() {
        viewModel.getHotelInfo()
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.state.collect {
                handleState(it)
            }
        }
    }

    private fun handleState(state: HotelInfoState) {
        when (state) {
            HotelInfoState.Initial -> showInitialState()
            HotelInfoState.Loading -> showLoadingState()
            is HotelInfoState.Success -> showSuccessState(state.hotelInfo)
            HotelInfoState.Error -> showErrorState()
        }
    }

    private fun setHotelAdapter() {
        hotelInfoAdapter = HotelInfoAdapter()
        binding.hotelInfoRecyclerView.adapter = hotelInfoAdapter
    }

    private fun showInitialState() {
        setViewsVisibility()
    }

    private fun showLoadingState() {
        setViewsVisibility(hotelInfoProgressBar = true)
    }

    private fun showSuccessState(hotelInfo: HotelInfoModel) {
        val listOfHotels: MutableList<ListItem> = hotelInfoToHotelInfoItem(hotelInfo)

        setViewsVisibility(hotelInfoRecyclerView = true, roomChoosingButton = true)

        viewLifecycleOwner.lifecycleScope.launch {
            hotelInfoAdapter.apply {
                items = listOfHotels
            }
        }

        setOnButtonClick(hotelInfo)
    }

    private fun hotelInfoToHotelInfoItem(hotelInfo: HotelInfoModel): MutableList<ListItem> {
        val listOfHotels = mutableListOf<HotelInfoModel>()
        val listOfItems =
            mutableListOf<ListItem>()
        listOfHotels.add(
            HotelInfoModel(
                hotelInfo.id,
                hotelInfo.name,
                hotelInfo.address,
                hotelInfo.minimalPrice,
                hotelInfo.priceForIt,
                hotelInfo.rating,
                hotelInfo.ratingName,
                hotelInfo.imageUrls,
                hotelInfo.aboutTheHotel,
            )
        )
        addAboutTheHotelInfo(listOfHotels, listOfItems)

        return listOfItems
    }

    private fun addAboutTheHotelInfo(
        listOfHotels: MutableList<HotelInfoModel>,
        listOfItems: MutableList<ListItem>
    ) {
        listOfHotels.forEach {
            listOfItems.add(it)
            listOfItems.add(
                AboutTheHotelModel(
                    it.aboutTheHotel?.description,
                    it.aboutTheHotel?.peculiarities
                )
            )
        }
    }

    private fun setOnButtonClick(hotelInfo: HotelInfoModel) {
        binding.roomChoosingButton.setOnClickListener {
            hotelInfo.name?.let { hotelName -> viewModel.openRoomInfo(hotelName) }
        }
    }

    private fun showErrorState() {
        setViewsVisibility(errorImageView = true, errorTextView = true, tryAgainButton = true)
    }

    private fun setViewsVisibility(
        hotelInfoProgressBar: Boolean = false,
        hotelInfoRecyclerView: Boolean = false,
        errorImageView: Boolean = false,
        errorTextView: Boolean = false,
        tryAgainButton: Boolean = false,
        roomChoosingButton: Boolean = false
    ) {
        binding.hotelInfoProgressBar.isVisible = hotelInfoProgressBar
        binding.hotelInfoRecyclerView.isVisible = hotelInfoRecyclerView
        binding.errorImageView.isVisible = errorImageView
        binding.errorTextView.isVisible = errorTextView
        binding.tryAgainButton.isVisible = tryAgainButton
        binding.roomChoosingButton.isEnabled = roomChoosingButton
    }

    private fun setTryAgainButton() {
        binding.tryAgainButton.setOnClickListener {
            viewModel.getHotelInfo()
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}