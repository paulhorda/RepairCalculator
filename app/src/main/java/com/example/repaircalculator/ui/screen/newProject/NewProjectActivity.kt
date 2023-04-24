package com.example.repaircalculator.ui.screen.newProject

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.repaircalculator.R
import com.example.repaircalculator.databinding.ActivityNewProjectBinding
import com.google.android.material.datepicker.MaterialDatePicker
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class NewProjectActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNewProjectBinding
    private val viewModel: NewProjectViewModel by viewModels()
    private var imageUri: Uri? = null

    private val SELECT_PICTURE = 200

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

        binding.imageMb.setOnClickListener {
            imageChooser()
        }

        binding.addNewProjectBtn.setOnClickListener {
            viewModel.insertProject(binding.projectNewNameTil.editText?.text.toString(),
                binding.projectNewAddressTil.editText?.text.toString(),
                datePickerStart.selection ?: 0,
                datePickerEnd.selection ?: 0, imageUri)
            finish()
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {

            // compare the resultCode with the
            // SELECT_PICTURE constant
            if (requestCode == SELECT_PICTURE) {
                // Get the url of the image from data
                val selectedImageUri: Uri? = data?.data
                if (null != selectedImageUri) {
                    // update the preview image in the layout
//                    binding.imageIv.setImageURI(selectedImageUri)
                    binding.imageMb.setBackgroundColor(resources.getColor(R.color.success))
                    imageUri = selectedImageUri

                }
            }
        }
    }


    private fun imageChooser() {

        // create an instance of the
        // intent of the type image
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        intent.type = "image/*"
        val REQUEST_CODE = 101
        startActivityForResult(intent, SELECT_PICTURE)

        // pass the constant to compare it
        // with the returned requestCode
//        startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE)
    }
}