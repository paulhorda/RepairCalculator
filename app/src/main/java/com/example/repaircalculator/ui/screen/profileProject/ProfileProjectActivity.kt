package com.example.repaircalculator.ui.screen.profileProject

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.repaircalculator.R
import com.example.repaircalculator.databinding.ActivityNewProjectBinding
import com.google.android.material.datepicker.MaterialDatePicker
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileProjectActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNewProjectBinding
//    private val viewModel: NewProjectViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNewProjectBinding.inflate(layoutInflater).also { setContentView(it.root) }
        binding.materialToolbar.title = getString(R.string.new_project)
        setSupportActionBar(binding.materialToolbar)

        val datePickerStart = MaterialDatePicker.Builder.datePicker()
            .setTitleText(R.string.select_date_start)
            .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
            .build()

        val datePickerEnd = MaterialDatePicker.Builder.datePicker()
            .setTitleText(R.string.select_date_end)
            .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
            .build()

        binding.dataStartMb.setOnClickListener {
            datePickerStart.show(supportFragmentManager, "tag");
        }

        binding.dataEndMb.setOnClickListener {
            datePickerEnd.show(supportFragmentManager, "tag");
        }

        binding.addNewProjectBtn.setOnClickListener {
//            viewModel.insertProject(binding.projectNewNameTil.editText?.text.toString(),
//                binding.projectNewAddressTil.editText?.text.toString(),
//                datePickerStart.selection ?: 0,
//                datePickerEnd.selection ?: 0)
            finish()
        }
    }
}