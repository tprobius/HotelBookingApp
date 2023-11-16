package com.tprobius.hotelbookingapp.features.hotel.presentation.hotelinfofragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import com.tprobius.hotelbookingapp.features.hotel.databinding.FragmentHotelInfoBinding
import com.tprobius.hotelbookingapp.features.hotel.domain.model.AboutTheHotelModel
import com.tprobius.hotelbookingapp.features.hotel.domain.model.HotelInfoModel
import com.tprobius.hotelbookingapp.features.hotel.presentation.adapterdelegates.HotelInfoDelegates
import com.tprobius.hotelbookingapp.features.hotel.presentation.adapterdelegates.ListItem
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class HotelInfoFragment : Fragment() {
    private val viewModel: HotelInfoViewModel by viewModel()

    private var _binding: FragmentHotelInfoBinding? = null
    private val binding
        get() = checkNotNull(_binding) { "Binding isn't initialized" }

    private lateinit var hotelAdapter: ListDelegationAdapter<List<ListItem>>

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
        viewModel.getHotelInfo()

        setHotelAdapter()
        setHandleState()
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
        hotelAdapter = ListDelegationAdapter(
            HotelInfoDelegates.hotelInfoDelegate(),
            HotelInfoDelegates.aboutTheHotelDelegate()
        )
        binding.recyclerView.adapter = hotelAdapter
    }

    private fun setHandleState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.state.collect {
                handleState(it)
            }
        }
    }

    private fun showInitialState() {
//        TODO("Not yet implemented")
    }

    private fun showLoadingState() {
//        TODO("Not yet implemented")
    }

    private fun showSuccessState(hotelInfo: HotelInfoModel) {
        val listOfHotels: MutableList<ListItem> = hotelInfoToHotelInfoItem(hotelInfo)
        viewLifecycleOwner.lifecycleScope.launch {
            hotelAdapter.apply {
                items = listOfHotels
                notifyDataSetChanged()
            }
        }
    }

    private fun hotelInfoToHotelInfoItem(hotelInfo: HotelInfoModel): MutableList<ListItem> {
        val listOfHotels = mutableListOf<HotelInfoModel>()
        val listOfItems = mutableListOf<ListItem>()
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

    private fun showErrorState() {
//        TODO("Not yet implemented")
    }
}