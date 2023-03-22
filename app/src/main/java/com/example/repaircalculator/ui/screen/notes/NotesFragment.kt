package com.example.repaircalculator.ui.screen.notes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.app.ActivityCompat.recreate
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.repaircalculator.data.entity.Note
import com.example.repaircalculator.databinding.FragmentNotesBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotesFragment : Fragment() {

    private lateinit var binding: FragmentNotesBinding

    private val args: NotesFragmentArgs by navArgs()

    private val viewModel: NotesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentNotesBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setLayoutManager()

        viewModel.setNotes(args.roomId)

        viewModel.adapter.noteCallback = {
            val action = NotesFragmentDirections.actionNotesFragmentToNoteFragment(it.id)
            view.findNavController().navigate(action)
        }

        binding.newNoteEfab.setOnClickListener{
            viewModel.insertNote(Note(0, args.roomId, "", 1, System.currentTimeMillis()))
            viewModel.setNotes(args.roomId)
        }
    }

    private fun setLayoutManager() {
        val dividerItemDecoration = DividerItemDecoration(requireContext(), LinearLayout.VERTICAL)

        binding.notesListRv.adapter = viewModel.adapter
//        binding.roomsRv.addItemDecoration(dividerItemDecoration)
    }

}