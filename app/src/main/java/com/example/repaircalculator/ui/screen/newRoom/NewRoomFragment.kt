package com.example.repaircalculator.ui.screen.newRoom

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.example.repaircalculator.data.entity.Room
import com.example.repaircalculator.databinding.FragmentNewRoomBinding
import com.example.repaircalculator.ui.screen.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewRoomFragment : Fragment() {

    private lateinit var binding: FragmentNewRoomBinding

    private val args: NewRoomFragmentArgs by navArgs()

    private val viewModel: NewRoomViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentNewRoomBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.addNewRoomBtn.setOnClickListener {
            val room = Room(0,
                args.projectId,
                binding.roomNewNameTil.editText?.text.toString(),
                binding.roomNewHeightTil.editText?.text.toString().toDouble(),
                binding.roomNewSquareTil.editText?.text.toString().toDouble(),
                "",
                System.currentTimeMillis()
            )

            viewModel.insertRoom(room)
            view.findNavController().popBackStack()
        }

        binding.addPlanBtn.setOnClickListener {
            val intent = Intent(requireContext(), com.example.repaircalculator.ui.screen.planPaint.MainActivity::class.java)
            intent.putExtra("id", 0)
            startActivity(intent)
        }
    }

}