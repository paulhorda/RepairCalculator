package com.example.repaircalculator.ui.screen.note

import android.Manifest.permission.CAMERA
import android.app.Activity
import android.app.AlertDialog
import android.content.ContentValues
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.*
import android.widget.ImageView
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.example.repaircalculator.R
import com.example.repaircalculator.databinding.FragmentNoteBinding
import com.example.repaircalculator.ui.screen.modalMaterialNew.ModalBottomSheetViewModel
import com.example.repaircalculator.ui.screen.modalPrice.ModalBottomSheet
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NoteFragment : Fragment() {

    private lateinit var binding: FragmentNoteBinding
    private val viewModel: NoteViewModel by viewModels()
    private val args: NoteFragmentArgs by navArgs()

    private val CAMERA_PERMISSION_CODE = 1000
    private val IMAGE_CAPTURE_CODE = 1001
    private var imageUri: Uri? = null
    private var imageView: ImageView? = null

    private val viewModelMaterial: ModalBottomSheetViewModel by viewModels()

    private lateinit var materialAlertDialogBuilder: MaterialAlertDialogBuilder
    private lateinit var customAlertDialogView: View

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentNoteBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


//        (requireActivity() as MenuHost).addMenuProvider(object : MenuProvider {
//            override fun onPrepareMenu(menu: Menu) {
//                // Handle for example visibility of menu items
//            }
//
//            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
//                menuInflater.inflate(R.menu.price_menu, menu)
//            }
//
//            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
//                when (menuItem.itemId) {
//                    R.id.price_i -> {
//                        val modalBottomSheet = ModalBottomSheet(args.noteId)
//                        modalBottomSheet.show(requireActivity().supportFragmentManager,
//                            ModalBottomSheet.TAG)
//                    }
//                }
//                return true
//            }
//        }, viewLifecycleOwner, Lifecycle.State.RESUMED)

        binding.noteListRv.adapter = viewModel.adapter
        binding.materialsListRv.adapter = viewModel.materialsAdapter
        binding.noteListRv.isNestedScrollingEnabled = false
        binding.materialsListRv.isNestedScrollingEnabled = false

        lifecycleScope.launch {
            val note = viewModel.getNote(args.noteId)
            binding.noteTitleEt.setText(note?.title.toString())
            viewModel.adapter.notes = viewModel.getNoteElements(args.noteId) ?: emptyList()

            if (note != null) {
                if(note.type.resource == R.string.note){
                    val visibility = View.GONE
                    val unVisibility = View.VISIBLE
                    binding.nameTableLl.visibility = visibility
                    binding.addItemBtn.visibility = visibility
                    binding.addImageBtn.visibility = unVisibility
                }
                else{
                    val visibility = View.VISIBLE
                    val unVisibility = View.GONE
                    binding.nameTableLl.visibility = visibility
                    binding.addItemBtn.visibility = visibility
                    binding.addImageBtn.visibility = unVisibility
                }
            }

            viewModel.materialsAdapter.materials =
                viewModel.getMaterialsElements(args.noteId) ?: emptyList()

            if (viewModel.materialsAdapter.materials.isEmpty()) {
                binding.resultMaterialsTv.visibility = View.GONE
                binding.priceMaterialsTv.visibility = View.GONE
            } else {
                binding.resultMaterialsTv.visibility = View.VISIBLE
                binding.priceMaterialsTv.visibility = View.VISIBLE
                binding.priceMaterialsTv.text =
                    viewModel.materialsAdapter.materials.sumOf { it.price * it.count }.toString()
            }
        }

        binding.addImageBtn.setOnClickListener {
            val permissionGranted = requestCameraPermission()
            if (permissionGranted) {
                openCameraInterface()
                viewModel.insertElementImg(args.noteId, imageUri!!)
                lifecycleScope.launch {
                    viewModel.adapter.notes = viewModel.getNoteElements(args.noteId) ?: emptyList()
                }
            }
        }

        binding.saveBtn.setOnClickListener {
            lifecycleScope.launch {
                val note = viewModel.getNote(args.noteId)!!
                note.title = binding.noteTitleEt.text.toString()
                viewModel.updateNote(note)
            }
        }

        viewModelMaterial.adapter.noteCallback = {
            materialAlertDialogBuilder = MaterialAlertDialogBuilder(requireContext())

            customAlertDialogView = LayoutInflater.from(requireContext())
                .inflate(R.layout.modal_material, null, false)

            materialAlertDialogBuilder.setView(customAlertDialogView)
                .setTitle("Upa material")
                .setPositiveButton("OK") { _: DialogInterface, position: Int ->

//                    viewModelMaterial.updateMaterial(args.noteId,
//                        customAlertDialogView.findViewById<TextInputLayout>(R.id.title_til).editText?.text.toString(),
//                        customAlertDialogView.findViewById<TextInputLayout>(R.id.count_til).editText?.text.toString()
//                            .toIntOrNull() ?: 0,
//                        customAlertDialogView.findViewById<TextInputLayout>(R.id.price_til).editText?.text.toString()
//                            .toDoubleOrNull() ?: 0.0)
                    lifecycleScope.launch {

                        val material = viewModelMaterial.getMaterial(position)
                        if (material != null) {
                            material.title =
                                customAlertDialogView.findViewById<TextInputLayout>(R.id.title_til).editText?.text.toString()
                            material.count =
                                customAlertDialogView.findViewById<TextInputLayout>(R.id.count_til).editText?.text.toString()
                                    .toIntOrNull() ?: 0
                            material.price =
                                customAlertDialogView.findViewById<TextInputLayout>(R.id.price_til).editText?.text.toString()
                                    .toDoubleOrNull() ?: 0.0
                            viewModelMaterial.updateMaterial(material)
                        }

                    }

                    val action = NoteFragmentDirections.actionNoteFragmentSelf(args.noteId)
                    view.findNavController().navigate(action)
                }
                .show()

        }

        binding.addItemBtn.setOnClickListener {
//            viewModel.insertElementText(args.noteId, "")
//
//            lifecycleScope.launch {
//                viewModel.adapter.notes = viewModel.getNoteElements(args.noteId) ?: emptyList()
//            }
//            val modalBottomSheet = com.example.repaircalculator.ui.screen.modalMaterialNew.ModalBottomSheet(args.noteId)
//            modalBottomSheet.show(requireActivity().supportFragmentManager, ModalBottomSheet.TAG)

            materialAlertDialogBuilder = MaterialAlertDialogBuilder(requireContext())

            customAlertDialogView = LayoutInflater.from(requireContext())
                .inflate(R.layout.modal_material, null, false)

            materialAlertDialogBuilder.setView(customAlertDialogView)
                .setTitle("New material")
                .setPositiveButton("OK") { _: DialogInterface, _: Int ->
                    viewModelMaterial.insertMaterial(args.noteId,
                        customAlertDialogView.findViewById<TextInputLayout>(R.id.title_til).editText?.text.toString(),
                        customAlertDialogView.findViewById<TextInputLayout>(R.id.count_til).editText?.text.toString()
                            .toIntOrNull() ?: 0,
                        customAlertDialogView.findViewById<TextInputLayout>(R.id.price_til).editText?.text.toString()
                            .toDoubleOrNull() ?: 0.0)
                    val action = NoteFragmentDirections.actionNoteFragmentSelf(args.noteId)
                    view.findNavController().navigate(action)
                }
                .show()


        }
    }

    //
//    fun refreshFragment (context: Context?) {
//        context?.let {
//           val contextManager = ( context as? AppCompatActivity)?.supportFragmentManager
//            fragmentManager?. let {
//                val currentFragment = binding
//            }
//        }
//    }
    private fun requestCameraPermission(): Boolean {
        var permissionGranted = false

        // If system os is Marshmallow or Above, we need to request runtime permission
        val cameraPermissionNotGranted = checkSelfPermission(activity as Context,
            CAMERA) == PackageManager.PERMISSION_DENIED
        if (cameraPermissionNotGranted) {
            val permission = arrayOf(CAMERA)

            // Display permission dialog
            requestPermissions(permission, CAMERA_PERMISSION_CODE)
        } else {
            // Permission already granted
            permissionGranted = true
        }

        return permissionGranted
    }

    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
    ) {
        if (requestCode === CAMERA_PERMISSION_CODE) {
            if (grantResults.size === 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission was granted
                openCameraInterface()
            } else {
                // Permission was denied
                showAlert("Camera permission was denied. Unable to take a picture.");
            }
        }
    }

    private fun openCameraInterface() {
        val values = ContentValues()
        values.put(MediaStore.Images.Media.TITLE, R.string.take_picture)
        values.put(MediaStore.Images.Media.DESCRIPTION, R.string.take_picture_description)
        imageUri =
            activity?.contentResolver?.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)

        // Create camera intent
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)

        // Launch intent
        startActivityForResult(intent, IMAGE_CAPTURE_CODE)
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Callback from camera intent
        if (resultCode == Activity.RESULT_OK) {
            // Set image captured to image view
            imageView?.setImageURI(imageUri)
            lifecycleScope.launch {
                viewModel.adapter.notes = viewModel.getNoteElements(args.noteId) ?: emptyList()
            }
        } else {
            // Failed to take picture
            showAlert("Failed to take camera picture")
        }
    }

    private fun showAlert(message: String) {
        val builder = AlertDialog.Builder(activity as Context)
        builder.setMessage(message)
        builder.setPositiveButton(R.string.ok_button_title, null)

        val dialog = builder.create()
        dialog.show()
    }
}