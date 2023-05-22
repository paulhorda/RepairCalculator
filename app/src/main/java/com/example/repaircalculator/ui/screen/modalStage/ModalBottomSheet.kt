package com.example.repaircalculator.ui.screen.modalStage

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.repaircalculator.R
import com.example.repaircalculator.data.entity.Stage
import com.example.repaircalculator.databinding.ModalStageBinding
import com.example.repaircalculator.databinding.NotePriceBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.button.MaterialButton
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ModalBottomSheet(private val projectId: Int) : BottomSheetDialogFragment() {

    private lateinit var binding: NotePriceBinding

    private val viewModel: ModalBottomSheetViewModel by viewModels()

    private lateinit var materialAlertDialogBuilder: MaterialAlertDialogBuilder
    private lateinit var customAlertDialogView: View

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = NotePriceBinding.inflate(layoutInflater, container, false)
        binding.addNewPriceBtn.setText(R.string.new_material)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        binding.priceListRv.adapter = viewModel.adapter

//        viewModel.setPricesForNote(noteId)
        viewModel.setStages(projectId)
        val datePickerStart = MaterialDatePicker.Builder.datePicker()
            .setTitleText(R.string.select_date_start)
            .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
            .build()

        val datePickerEnd = MaterialDatePicker.Builder.datePicker()
            .setTitleText(R.string.select_date_end)
            .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
            .build()

        materialAlertDialogBuilder = MaterialAlertDialogBuilder(requireContext())

        viewModel.adapter.stageCallback = {

            val finishText = if(it.dateEnd >= System.currentTimeMillis() && it.dateStart <= System.currentTimeMillis()) "Відновити" else if(it.isFinish) "Завершити" else ""
            val finishState = if(it.dateEnd >= System.currentTimeMillis() && it.dateStart <= System.currentTimeMillis()) true else if(it.isFinish) false else it.isFinish

            materialAlertDialogBuilder = MaterialAlertDialogBuilder(requireContext())


            val b = ModalStageBinding.inflate(layoutInflater, view as ViewGroup, false)

            b.titleTil.editText?.setText(it.title)

            val datePickerStartStage = MaterialDatePicker.Builder.datePicker()
                .setTitleText(R.string.select_date_start)
                .setSelection(it.dateStart)
                .build()

            val datePickerEndStage = MaterialDatePicker.Builder.datePicker()
                .setTitleText(R.string.select_date_end)
                .setSelection(it.dateEnd)
                .build()

            b.dataStartMb.setOnClickListener {
                datePickerStartStage.show(parentFragmentManager, "tag");
            }

            b.dataEndMb.setOnClickListener {
                datePickerEndStage.show(parentFragmentManager, "tag");
            }

            materialAlertDialogBuilder.setView(b.root)
                .setTitle("Змінити етап")
                .setPositiveButton("OK") { d: DialogInterface, _: Int ->

                    viewModel.updateStage(
                        Stage(
                            it.id,
                            it.projectId,
                            if (b.titleTil.editText?.text?.isNotBlank() == true) b.titleTil.editText?.text.toString() else it.title,
                            datePickerStartStage.selection ?: 0,
                            datePickerEndStage.selection ?: 0,
                            null,
                            it.isFinish,
                            System.currentTimeMillis()
                        )
                    )
                }
                .setNegativeButton("Відмінити") { _: DialogInterface, _: Int ->

                }
                .setNeutralButton(finishText){ _: DialogInterface, _: Int ->

                    viewModel.updateStage(
                        Stage(
                            it.id,
                            it.projectId,
                            if (b.titleTil.editText?.text?.isNotBlank() == true) b.titleTil.editText?.text.toString() else it.title,
                            datePickerStartStage.selection ?: 0,
                            datePickerEndStage.selection ?: 0,
                            null,
                            finishState,
                            System.currentTimeMillis()
                        )
                    )
                }
                .show()

        }

        binding.addNewPriceBtn.setOnClickListener {

            materialAlertDialogBuilder = MaterialAlertDialogBuilder(requireContext())

            customAlertDialogView = layoutInflater
                .inflate(R.layout.modal_stage, null, false)

            customAlertDialogView.findViewById<MaterialButton>(R.id.data_start_mb)
                .setOnClickListener {
                    datePickerStart.show(parentFragmentManager, "tag");
                }

            customAlertDialogView.findViewById<MaterialButton>(R.id.data_end_mb)
                .setOnClickListener {
                    datePickerEnd.show(parentFragmentManager, "tag");
                }

            materialAlertDialogBuilder.setView(customAlertDialogView)
                .setTitle("Новий етап")
                .setPositiveButton("OK") { _: DialogInterface, _: Int ->
                    val title = customAlertDialogView.findViewById<TextInputLayout>(R.id.title_til)

                    viewModel.insertStage(
                        Stage(
                            0,
                            projectId,
                            title.editText?.text.toString(),
                            datePickerStart.selection ?: 0,
                            datePickerEnd.selection ?: 0,
                            null,
                            false,
                            System.currentTimeMillis()
                        )
                    )
                }
                .setNegativeButton("Відмінити") { _: DialogInterface, _: Int ->

                }
                .show()

        }
    }

    companion object {
        const val TAG = "ModalBottomSheet"
    }
}
