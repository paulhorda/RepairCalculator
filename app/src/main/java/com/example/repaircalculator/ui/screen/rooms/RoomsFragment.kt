package com.example.repaircalculator.ui.screen.rooms

import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.example.repaircalculator.R
import com.example.repaircalculator.databinding.FragmentRoomsBinding
import com.example.repaircalculator.ui.screen.modalStage.ModalBottomSheet
import com.yalantis.ucrop.UCrop
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.io.File


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

//        val menuHost: MenuHost = requireActivity()
//        menuHost.addMenuProvider(object : MenuProvider {
//            override fun onPrepareMenu(menu: Menu) {
//                // Handle for example visibility of menu items
//            }
//
//            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
//                menuInflater.inflate(R.menu.paint_room_menu, menu)
//            }
//
//            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
//                when (menuItem.itemId) {
//                    R.id.log_out -> {
//                        lifecycleScope.launch {
//
//                            val modalBottomSheet = ModalBottomSheet(projectId)
//                        modalBottomSheet.show(requireActivity().supportFragmentManager,
//                            ModalBottomSheet.TAG)
//
//                        }
//                    }
//                }
//                return true
//            }
//        }, viewLifecycleOwner, Lifecycle.State.RESUMED)

    }

    private fun setLayoutManager() {
//        val dividerItemDecoration = DividerItemDecoration(requireContext(), LinearLayout.VERTICAL)

        binding.roomsRv.adapter = viewModel.adapter
//        binding.roomsRv.addItemDecoration(dividerItemDecoration)
    }

}