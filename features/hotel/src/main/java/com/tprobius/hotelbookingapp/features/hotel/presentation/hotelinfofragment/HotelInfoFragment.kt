package com.tprobius.hotelbookingapp.features.hotel.presentation.hotelinfofragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import com.tprobius.hotelbookingapp.features.hotel.databinding.FragmentHotelInfoBinding
import com.tprobius.hotelbookingapp.features.hotel.domain.model.AboutTheHotelModel
import com.tprobius.hotelbookingapp.features.hotel.domain.model.HotelInfoModel
import com.tprobius.hotelbookingapp.features.hotel.presentation.adapterdelegates.HotelInfoDelegates
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class HotelInfoFragment : Fragment() {
    private val viewModel: HotelInfoViewModel by viewModel()

    private var _binding: FragmentHotelInfoBinding? = null
    private val binding
        get() = checkNotNull(_binding) { "Binding isn't initialized" }

    private lateinit var hotelInfoAdapter: ListDelegationAdapter<List<com.tprobius.hotelbookingapp.utils.recyclerviewadapter.ListItem>>

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
        hotelInfoAdapter = ListDelegationAdapter(
            HotelInfoDelegates.hotelInfoDelegate(),
            HotelInfoDelegates.aboutTheHotelDelegate()
        )
        binding.hotelInfoRecyclerView.adapter = hotelInfoAdapter
    }

    private fun showInitialState() {
        binding.topAppBarLinearLayout.isVisible = true
        binding.hotelInfoProgressBar.isVisible = false
        binding.hotelInfoRecyclerView.isVisible = false
        binding.errorImageView.isVisible = false
        binding.errorTextView.isVisible = false
        binding.tryAgainButton.isVisible = false
        binding.bottomAppBarLinearLayout.isVisible = true
        binding.roomChoosingButton.isEnabled = false
    }

    private fun showLoadingState() {
        binding.topAppBarLinearLayout.isVisible = true
        binding.hotelInfoProgressBar.isVisible = true
        binding.hotelInfoRecyclerView.isVisible = false
        binding.errorImageView.isVisible = false
        binding.errorTextView.isVisible = false
        binding.tryAgainButton.isVisible = false
        binding.bottomAppBarLinearLayout.isVisible = true
        binding.roomChoosingButton.isEnabled = false
    }

    private fun showSuccessState(hotelInfo: HotelInfoModel) {
        val listOfHotels: MutableList<com.tprobius.hotelbookingapp.utils.recyclerviewadapter.ListItem> =
            hotelInfoToHotelInfoItem(hotelInfo)

        binding.topAppBarLinearLayout.isVisible = true
        binding.hotelInfoProgressBar.isVisible = false
        binding.hotelInfoRecyclerView.isVisible = true
        binding.errorImageView.isVisible = false
        binding.errorTextView.isVisible = false
        binding.tryAgainButton.isVisible = false
        binding.bottomAppBarLinearLayout.isVisible = true
        binding.roomChoosingButton.isEnabled = true

        viewLifecycleOwner.lifecycleScope.launch {
            hotelInfoAdapter.apply {
                items = listOfHotels
                notifyDataSetChanged()
            }
        }

        setOnButtonClick(hotelInfo)
    }

    private fun hotelInfoToHotelInfoItem(hotelInfo: HotelInfoModel): MutableList<com.tprobius.hotelbookingapp.utils.recyclerviewadapter.ListItem> {
        val listOfHotels = mutableListOf<HotelInfoModel>()
        val listOfItems =
            mutableListOf<com.tprobius.hotelbookingapp.utils.recyclerviewadapter.ListItem>()
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
        listOfItems: MutableList<com.tprobius.hotelbookingapp.utils.recyclerviewadapter.ListItem>
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
        binding.topAppBarLinearLayout.isVisible = true
        binding.hotelInfoProgressBar.isVisible = false
        binding.hotelInfoRecyclerView.isVisible = false
        binding.errorImageView.isVisible = true
        binding.errorTextView.isVisible = true
        binding.tryAgainButton.isVisible = true
        binding.bottomAppBarLinearLayout.isVisible = true
        binding.roomChoosingButton.isEnabled = false
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