package com.example.repaircalculator.ui.screen.statistics

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.moneysaver.utils.toDate
import com.example.repaircalculator.R
import com.example.repaircalculator.data.entity.Material
import com.example.repaircalculator.data.entity.Room
import com.example.repaircalculator.data.entity.Stage
import com.example.repaircalculator.databinding.FragmentStatisticsBinding
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.IAxisValueFormatter
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import com.github.mikephil.charting.utils.MPPointF
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit


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
        binding.recyclerViewRooms.adapter = viewModel.roomsStatisticsAdapter

        viewModel.roomsStatisticsAdapter.materialsCallback = {
            val action = StatisticsFragmentDirections.actionStatisticsFragmentToTableFragment(it.id)
            view.findNavController().navigate(action)
        }

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


            var map = mutableListOf<Pair<Room, List<Material>>>()
            val rooms = viewModel.getRooms(projectId)

            rooms.forEach { room ->
                val materials = viewModel.getMaterials(room.id)
                map.add(
                    Pair(room, materials)
                )
            }

            viewModel.roomsStatisticsAdapter.mapOfMaterials = map
        }

        binding.tableMb.setOnClickListener {
            val action = StatisticsFragmentDirections.actionStatisticsFragmentToTableFragment(0)
            view.findNavController().navigate(action)
        }

        lifecycleScope.launch {
            val rooms = viewModel.getRooms(projectId)
            val values = mutableListOf<PieEntry>()
            var sum = 0.0

            rooms.forEach { room ->
                val sumNotes = viewModel.getMaterials(room.id).sumOf { it.price * it.count }
                values.add(
                    PieEntry(
                        sumNotes.toFloat(),
                        room.name
                    )
                )
                sum += sumNotes
            }

            binding.categoriesPieChart.legend.isEnabled = false
            binding.categoriesPieChart.description.isEnabled = false

            binding.categoriesPieChart.centerText =
                resources.getString(R.string.uah_amount_placeholder, sum.toString())
            binding.categoriesPieChart.setCenterTextSize(22f)
            val dataSet = PieDataSet(values, "")

            dataSet.sliceSpace = 3f
            dataSet.iconsOffset = MPPointF(0f, 40f)
            dataSet.selectionShift = 5f
            dataSet.valueTextSize = 12f
            dataSet.color = resources.getColor(R.color.shakespeare, null)

            val data1 = PieData(dataSet)
            data1.setValueFormatter(PercentFormatter(binding.categoriesPieChart))
            data1.setValueTextSize(11f)
            data1.setValueTextColor(Color.WHITE)

            binding.categoriesPieChart.data = data1
            binding.categoriesPieChart.notifyDataSetChanged();

            binding.categoriesPieChart.invalidate()


        }


//        lifecycleScope.launch {
//            binding.horizontalChart.legend.isEnabled = false
//            binding.horizontalChart.description.isEnabled = false
//            val entries = mutableListOf<BarEntry>()
//
//            entries.add(BarEntry(10f, 8f))
//
//            val projectId = activity?.intent?.getIntExtra("KEY_PROJECT", 0) ?: 0
//
//            var count = 0
//            viewModel.getStages(projectId).collect { stages ->
//                Log.d("sldfkjslkd", stages.toString())
//
//                stages.forEach {
//                    entries.add(
//                        BarEntry(
//                            count.toFloat()*2,
//                            TimeUnit.DAYS.convert(
//                                it.dateEnd.toDate().time - it.dateStart.toDate().time,
//                                TimeUnit.MILLISECONDS
//                            ).toFloat()
//                        )
//                    )
//                    Log.d("sldfkjslkd", entries.toString())
//                    count++
//                }
//            }
//
//            val data = BarData(BarDataSet(entries, "hi"))
//            binding.horizontalChart.data = data
//            binding.horizontalChart.notifyDataSetChanged()
//            binding.horizontalChart.invalidate()
//        }

        lifecycleScope.launch {
            val barChart = binding.horizontalChart

            // calling method to get bar entries.

            // calling method to get bar entries.
            var barEntriesArrayList = mutableListOf<BarEntry>()
            barEntriesArrayList.clear()

            // adding new entry to our array list with bar
            // entry and passing x and y axis value to it.

            // adding new entry to our array list with bar
//            // entry and passing x and y axis value to it.
//            barEntriesArrayList.add(BarEntry(1f, 4f))
//            barEntriesArrayList.add(BarEntry(2f, 6f))
//            barEntriesArrayList.add(BarEntry(3f, 8f))
//            barEntriesArrayList.add(BarEntry(4f, 2f))
//            barEntriesArrayList.add(BarEntry(5f, 4f))
//            barEntriesArrayList.add(BarEntry(6f, 1f))

            val projectId = activity?.intent?.getIntExtra("KEY_PROJECT", 0) ?: 0

            var count = 1
            viewModel.getStages(projectId)

            var stages = emptyList<Stage>()

            

            viewModel.liveData.observe(viewLifecycleOwner) { stageList ->
                val finished =  mutableListOf<BarEntry>()
                finished.clear()
                val inProgress =  mutableListOf<BarEntry>()
                inProgress.clear()
                val overdue =  mutableListOf<BarEntry>()
                overdue.clear()
                stages = stageList ?: emptyList()

                stages.forEach {
                    val bar: BarEntry
                    if (it.isFinish) {
                        bar = BarEntry(

                            count.toFloat(),
                            listOf(
                                TimeUnit.DAYS.convert(
                                    it.dateEnd.toDate().time - it.dateStart.toDate().time,
                                    TimeUnit.MILLISECONDS
                                ).toFloat()
                            ).toFloatArray()
                        )
                        finished.add(
                            bar
                        )
                        count += 1

                    } else if (System.currentTimeMillis()
                            .toDate() < it.dateEnd.toDate() && System.currentTimeMillis()
                            .toDate() > it.dateStart.toDate()
                    ) {
                        bar = BarEntry(

                            count.toFloat(),
                            listOf(
                                TimeUnit.DAYS.convert(
                                    System.currentTimeMillis()
                                        .toDate().time - it.dateStart.toDate().time,
                                    TimeUnit.MILLISECONDS
                                ).toFloat(), TimeUnit.DAYS.convert(
                                    it.dateEnd.toDate().time - System.currentTimeMillis()
                                        .toDate().time,
                                    TimeUnit.MILLISECONDS
                                ).toFloat()
                            ).toFloatArray()
                        )
                        inProgress.add(
                            bar
                        )
                        count += 1
                    } else if (System.currentTimeMillis().toDate() > it.dateEnd.toDate()) {
                        bar = BarEntry(

                            count.toFloat(),
                            listOf(
                                TimeUnit.DAYS.convert(
                                    it.dateEnd.toDate().time - it.dateStart.toDate().time,
                                    TimeUnit.MILLISECONDS
                                ).toFloat(), TimeUnit.DAYS.convert(
                                    System.currentTimeMillis()
                                        .toDate().time - it.dateEnd.toDate().time,
                                    TimeUnit.MILLISECONDS
                                ).toFloat()
                            ).toFloatArray()
                        )

                        overdue.add(
                            bar
                        )
                        count += 1
                    }
                }

                barChart.setVisibleXRangeMaximum(5f)

                val bars: MutableList<IBarDataSet> = ArrayList()
                val datasetFinished = BarDataSet(finished, "Finished")
                datasetFinished.color = requireActivity().getColor(R.color.shakespeare)
                bars.add(datasetFinished)
                val datasetInProgress = BarDataSet(inProgress, "In progress")
                datasetInProgress.colors = listOf(requireActivity().getColor(R.color.shakespeare), Color.GRAY)
                bars.add(datasetInProgress)
                val datasetOverdue = BarDataSet(overdue, "Overdue")
                datasetOverdue.colors = listOf(requireActivity().getColor(R.color.shakespeare), Color.RED)
                bars.add(datasetOverdue)
                val data = BarData(bars)
                    barChart.xAxis.position = XAxis.XAxisPosition.BOTTOM

                barChart.data = data
                datasetFinished.setDrawValues(true)
                datasetInProgress.setDrawValues(true)
                datasetOverdue.setDrawValues(true)
                    // Hide the description
                barChart.setDrawValueAboveBar(true)
                barChart.description.text = ""
                barChart.axisRight.setDrawLabels(false);
                barChart.xAxis.setDrawLabels(false);
//                barDataSet.setDrawValueAboveBar(true)
//
                datasetFinished.valueFormatter = object : ValueFormatter() {
                    override fun getBarLabel(barEntry: BarEntry?): String {
                        return barEntry?.y?.toString() ?: ""
                    }
                }

                datasetInProgress.valueFormatter = object : ValueFormatter() {
                    override fun getBarLabel(barEntry: BarEntry?): String {
                        return barEntry?.y?.toString() ?: ""
                    }
                }

                datasetOverdue.valueFormatter = object : ValueFormatter() {
                    override fun getBarLabel(barEntry: BarEntry?): String {
                        return barEntry?.y?.toString() ?: ""
                    }
                }

                    barChart.xAxis.valueFormatter = object : ValueFormatter() {
                    private val labels = arrayOf("Bar 1", "Bar 2", "Bar 3", "Bar 4")
                    override fun getFormattedValue(value: Float): String {
                        return labels.getOrElse(value.toInt()) { "" }
                    }
                }



                val legend = barChart.legend
                //setting the shape of the legend form to line, default square shape
                //setting the shape of the legend form to line, default square shape
                legend.form = Legend.LegendForm.LINE
                //setting the text size of the legend
                //setting the text size of the legend
                legend.textSize = 11f
                //setting the alignment of legend toward the chart
                //setting the alignment of legend toward the chart
                legend.verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM
                legend.horizontalAlignment = Legend.LegendHorizontalAlignment.LEFT
                //setting the stacking direction of legend
                //setting the stacking direction of legend
                legend.orientation = Legend.LegendOrientation.HORIZONTAL
                //setting the location of legend outside the chart, default false if not set
                //setting the location of legend outside the chart, default false if not set
                legend.setDrawInside(false)


                barChart.data.notifyDataChanged()

                barChart.axisRight.setDrawGridLines(false)
                barChart.axisLeft.setDrawGridLines(false)
                barChart.xAxis.setDrawGridLines(false)

                barChart.description.isEnabled = false
                binding.horizontalChart.invalidate()

            }
        }
    }

}