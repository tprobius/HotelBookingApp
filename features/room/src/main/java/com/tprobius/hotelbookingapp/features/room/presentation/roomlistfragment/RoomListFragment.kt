package com.tprobius.hotelbookingapp.features.room.presentation.roomlistfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import com.tprobius.hotelbookingapp.features.room.databinding.FragmentRoomInfoBinding
import com.tprobius.hotelbookingapp.features.room.domain.model.RoomInfoModel
import com.tprobius.hotelbookingapp.features.room.domain.model.RoomListModel
import com.tprobius.hotelbookingapp.features.room.presentation.adapterdelegates.RoomListDelegates
import com.tprobius.hotelbookingapp.utils.recyclerviewadapter.ListItem
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class RoomListFragment : Fragment() {
    private val viewModel: RoomListViewModel by viewModel {
        parametersOf(arguments?.hotelName)
    }

    private var _binding: FragmentRoomInfoBinding? = null
    private val binding
        get() = checkNotNull(_binding) { "Binding isn't initialized" }

    private lateinit var roomListAdapter: ListDelegationAdapter<List<ListItem>>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRoomInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHandleState()
        setRoomListAdapter()
        setOnBackClick()
        setTryAgainButton()
    }

    private fun setHandleState() {
        viewModel.getRoomInfo()
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.state.collect {
                handleState(it)
            }
        }
    }

    private fun handleState(state: RoomListState) {
        when (state) {
            RoomListState.Initial -> showInitialState()
            RoomListState.Loading -> showLoadingState()
            is RoomListState.Success -> showSuccessState(state.roomInfo)
            RoomListState.Error -> showErrorState()
        }
    }

    private fun setRoomListAdapter() {
        roomListAdapter = ListDelegationAdapter(
            RoomListDelegates.roomInfoDelegate { viewModel.openBookingInfo() }
        )
        binding.roomListRecyclerView.adapter = roomListAdapter
    }

    private fun showInitialState() {
        binding.topAppBarLinearLayout.isVisible = true
        binding.roomInfoTitleTextView.isVisible = true
        binding.roomListRecyclerView.isVisible = false
        binding.constraintLayout.isVisible = false
    }

    private fun showLoadingState() {
        binding.topAppBarLinearLayout.isVisible = true
        binding.roomInfoTitleTextView.isVisible = true
        binding.roomListRecyclerView.isVisible = false
        binding.constraintLayout.isVisible = true
        binding.progressBar.isVisible = true
        binding.errorImageView.isVisible = false
        binding.errorTextView.isVisible = false
        binding.tryAgainButton.isVisible = false
    }

    private fun showSuccessState(roomInfo: RoomListModel) {
        val listOfRooms = roomInfoToRoomInfoItem(roomInfo)

        binding.topAppBarLinearLayout.isVisible = true
        binding.roomInfoTitleTextView.isVisible = true
        binding.roomListRecyclerView.isVisible = true
        binding.constraintLayout.isVisible = false
        binding.progressBar.isVisible = false
        binding.errorImageView.isVisible = false
        binding.errorTextView.isVisible = false
        binding.tryAgainButton.isVisible = false

        viewLifecycleOwner.lifecycleScope.launch {
            roomListAdapter.apply {
                items = listOfRooms
                notifyDataSetChanged()
            }
        }
        binding.roomInfoTitleTextView.text = viewModel.getHotelName()
    }

    private fun roomInfoToRoomInfoItem(roomInfo: RoomListModel): MutableList<RoomInfoModel> {
        val listOfRooms = mutableListOf<RoomInfoModel>()
        roomInfo.rooms?.forEach { room ->
            listOfRooms.add(
                RoomInfoModel(
                    room?.id,
                    room?.name,
                    room?.price,
                    room?.pricePer,
                    room?.peculiarities,
                    room?.imageUrls
                )
            )
        }
        return listOfRooms
    }

    private fun showErrorState() {
        binding.topAppBarLinearLayout.isVisible = true
        binding.roomInfoTitleTextView.isVisible = true
        binding.roomListRecyclerView.isVisible = false
        binding.constraintLayout.isVisible = true
        binding.progressBar.isVisible = false
        binding.errorImageView.isVisible = true
        binding.errorTextView.isVisible = true
        binding.tryAgainButton.isVisible = true
    }

    private fun setOnBackClick() {
        binding.backImageView.setOnClickListener {
            viewModel.backToHotelInfo()
        }
    }

    private fun setTryAgainButton() {
        binding.tryAgainButton.setOnClickListener {
            viewModel.getRoomInfo()
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    companion object {
        private const val HOTEL_NAME = "HOTEL_NAME"
        private var Bundle.hotelName
            get() = getString(HOTEL_NAME)
            set(value) = putString(HOTEL_NAME, value)

        fun newInstance(hotelName: String) = RoomListFragment().apply {
            arguments = Bundle().apply { this.hotelName = hotelName }
        }
    }
}