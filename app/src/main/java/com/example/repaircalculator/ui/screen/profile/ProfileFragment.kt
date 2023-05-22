package com.example.repaircalculator.ui.screen.profile

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.repaircalculator.databinding.FragmentProfileBinding
import com.example.repaircalculator.ui.screen.modalStage.ModalBottomSheet
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.io.IOException
import java.io.InputStream


@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding

    private val viewModel: ProfileViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentProfileBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val projectId = activity?.intent?.getIntExtra("KEY_PROJECT", 0) ?: 0
        viewModel.updateLiveData(projectId)

        binding.stagesLl.setOnClickListener {
            lifecycleScope.launch {
                val modalBottomSheet = ModalBottomSheet(projectId)

                modalBottomSheet.show(
                    requireActivity().supportFragmentManager,
                    ModalBottomSheet.TAG
                )
            }
        }

        viewModel.project.observe(viewLifecycleOwner) {
            val plan = Uri.parse(it.image)

            val imageUri = Uri.parse(it.image)
            try {
                val inputStream: InputStream = requireActivity().contentResolver.openInputStream(imageUri)!!
                val bitmap = BitmapFactory.decodeStream(inputStream)
                binding.planIv.setImageBitmap(bitmap)
            } catch (e: IOException) {
                e.printStackTrace()
            }

            try {
//                val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
//                intent.type = "image/*"
//                startActivityForResult(intent, 101)
//
//                binding.planIv.setImageURI(plan)
//                if (Build.VERSION.SDK_INT < 19) {
//
//                    requireActivity().grantUriPermission(
//                        requireActivity().packageName,
//                        plan,
//                        Intent.FLAG_GRANT_READ_URI_PERMISSION
//                    );
//
//                    requireActivity().contentResolver.takePersistableUriPermission(
//                        plan,
//                        Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION
//                    )
//
//                    val bitmap = BitmapFactory.decodeStream(
//                        requireActivity().contentResolver.openInputStream(plan)
//                    );
//                    binding.planIv.setImageBitmap(bitmap)
//                }
            } catch (e: SecurityException) {
                e.printStackTrace()
            }

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 101 && resultCode == RESULT_OK) {
            val imageUri = data?.data
            binding.planIv.setImageURI(imageUri)
        }
    }

}