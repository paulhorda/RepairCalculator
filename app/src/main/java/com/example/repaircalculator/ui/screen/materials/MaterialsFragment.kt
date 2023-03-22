package com.example.repaircalculator.ui.screen.materials

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.example.repaircalculator.data.entity.Note
import com.example.repaircalculator.databinding.FragmentMaterialsBinding
import com.example.repaircalculator.databinding.FragmentNotesBinding
import com.example.repaircalculator.databinding.FragmentRoomsBinding
import com.example.repaircalculator.ui.screen.notes.NotesFragmentDirections
import com.example.repaircalculator.ui.screen.notes.NotesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MaterialsFragment : Fragment() {

    private lateinit var binding: FragmentNotesBinding

    private val viewModel: NotesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentNotesBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.notesListRv.adapter = viewModel.adapter
        val projectId = activity?.intent?.getIntExtra("KEY_PROJECT", 0) ?: 0

        viewModel.setNotes(0)

        viewModel.adapter.noteCallback = {
            val action = MaterialsFragmentDirections.actionMaterialsFragmentToNoteFragment(it.id)
            view.findNavController().navigate(action)
        }

        binding.newNoteEfab.setOnClickListener{
            viewModel.insertNote(Note(0, 0, "", 1, System.currentTimeMillis()))
            viewModel.setNotes(0)
        }
    }
}