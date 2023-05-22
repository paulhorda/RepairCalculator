package com.example.repaircalculator.ui.screen.tableScreen

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.example.repaircalculator.data.entity.Material
import com.example.repaircalculator.databinding.FragmentTableBinding
import com.example.repaircalculator.ui.screen.note.NoteFragmentArgs
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TableFragment : Fragment() {

    private lateinit var binding: FragmentTableBinding
    private val args: TableFragmentArgs by navArgs()
    private val viewModel: TableViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentTableBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.materialsListRv.adapter = viewModel.materialsAdapter
        val projectId = activity?.intent?.getIntExtra("KEY_PROJECT", 0) ?: 0

        if (args.roomId == 0)
            lifecycleScope.launch {
                val rooms = viewModel.getRooms(projectId)
                val materials = mutableListOf<Material>()

                rooms?.forEach { room ->
                    val notes = viewModel.getNotes(room.id)
                    notes?.forEach {
                        viewModel.getMaterialsElements(it.id)?.forEach { material ->
                            materials.add(material)
                        }
                    }
                }
                viewModel.materialsAdapter.materials = materials

                binding.priceMaterialsTv.text = materials.sumOf { it.price * it.count }.toString()
            }
        else {
            lifecycleScope.launch {
                val room = viewModel.getRoom(args.roomId)
                val materials = mutableListOf<Material>()

                 if(room!=null){
                    val notes = viewModel.getNotes(room.id)
                    notes?.forEach {
                        viewModel.getMaterialsElements(it.id)?.forEach { material ->
                            materials.add(material)
                        }
                    }
                 }

                viewModel.materialsAdapter.materials = materials

                binding.priceMaterialsTv.text = materials.sumOf { it.price * it.count }.toString()
            }
        }
    }
}