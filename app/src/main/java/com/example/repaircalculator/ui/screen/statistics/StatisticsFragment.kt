package com.example.repaircalculator.ui.screen.statistics

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.repaircalculator.R
import com.example.repaircalculator.databinding.FragmentRoomsBinding
import com.example.repaircalculator.databinding.FragmentStatisticsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class StatisticsFragment : Fragment() {

    private lateinit var binding: FragmentStatisticsBinding

    private val viewModel: StatisticsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentStatisticsBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.commonStatisticRv.adapter = viewModel.commonStatisticAdapter
        binding.commonStatisticRv.layoutManager = GridLayoutManager(requireContext(), 2)

        val projectId = activity?.intent?.getIntExtra("KEY_PROJECT", 0) ?: 0

        lifecycleScope.launch {
            val roomsNum = viewModel.getRooms(projectId)
            val notes = viewModel.getNotesForProject(projectId)
            val price = viewModel.getFullPriceFor(projectId)
//            val dateProgress = viewModel.getDateProgress(projectId)

            viewModel.commonStatisticAdapter.setData(
                mapOf(
                    R.string.num_rooms to roomsNum.count().toString(),
                    R.string.num_notes to notes.toString(),
//                    R.string.full_price to notes.toString(),
//                    R.string.date_progress to dateProgress.toString(),
                )
            )
        }


    }
}