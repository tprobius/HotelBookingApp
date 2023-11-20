package com.tprobius.hotelbookingapp.features.room.presentation.roomlistfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.tprobius.hotelbookingapp.features.room.databinding.FragmentRoomInfoBinding
import com.tprobius.hotelbookingapp.features.room.domain.model.RoomInfoModel
import com.tprobius.hotelbookingapp.features.room.domain.model.RoomListModel
import com.tprobius.hotelbookingapp.features.room.presentation.adapterdelegates.RoomListAdapter
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

    private lateinit var roomListAdapter: RoomListAdapter

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
        roomListAdapter = RoomListAdapter { viewModel.openBookingInfo() }
        binding.roomListRecyclerView.adapter = roomListAdapter
    }

    private fun showInitialState() {
        setViewsVisibility()
    }

    private fun showLoadingState() {
        setViewsVisibility(constraintLayout = true, progressBar = true)
    }

    private fun showSuccessState(roomInfo: RoomListModel) {
        val listOfRooms = roomInfoToRoomInfoItem(roomInfo)

        setViewsVisibility(roomListRecyclerView = true)

        viewLifecycleOwner.lifecycleScope.launch {
            roomListAdapter.apply {
                items = listOfRooms
            }
        }
        binding.roomInfoTitleTextView.text = viewModel.getHotelName()
    }

    private fun roomInfoToRoomInfoItem(roomInfo: RoomListModel): MutableList<ListItem> {
        val listOfRooms = mutableListOf<ListItem>()
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
        setViewsVisibility(
            constraintLayout = true,
            errorImageView = true,
            errorTextView = true,
            tryAgainButton = true
        )
    }

    private fun setViewsVisibility(
        roomListRecyclerView: Boolean = false,
        constraintLayout: Boolean = false,
        progressBar: Boolean = false,
        errorImageView: Boolean = false,
        errorTextView: Boolean = false,
        tryAgainButton: Boolean = false
    ) {
        binding.roomListRecyclerView.isVisible = roomListRecyclerView
        binding.constraintLayout.isVisible = constraintLayout
        binding.progressBar.isVisible = progressBar
        binding.errorImageView.isVisible = errorImageView
        binding.errorTextView.isVisible = errorTextView
        binding.tryAgainButton.isVisible = tryAgainButton
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