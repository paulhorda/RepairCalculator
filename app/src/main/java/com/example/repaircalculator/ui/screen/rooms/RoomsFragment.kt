package com.example.repaircalculator.ui.screen.rooms

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.example.repaircalculator.databinding.FragmentRoomsBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class RoomsFragment : Fragment() {

    private lateinit var binding: FragmentRoomsBinding

    private val viewModel: RoomsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentRoomsBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setLayoutManager()
        val projectId = activity?.intent?.getIntExtra("KEY_PROJECT", 0) ?: 0
        viewModel.setRooms(projectId)

        viewModel.adapter.roomCallback = {
            val action = RoomsFragmentDirections.actionRoomsFragmentToNotesFragment(it.id)
            view.findNavController().navigate(action)
        }

        binding.newRoomEfab.setOnClickListener {
            val action = RoomsFragmentDirections.actionRoomsFragmentToNewRoomFragment(projectId)
            view.findNavController().navigate(action)
        }
    }

    private fun setLayoutManager() {
//        val dividerItemDecoration = DividerItemDecoration(requireContext(), LinearLayout.VERTICAL)

        binding.roomsRv.adapter = viewModel.adapter
//        binding.roomsRv.addItemDecoration(dividerItemDecoration)
    }

}