package com.example.repaircalculator.ui.screen.note

import android.Manifest.permission.CAMERA
import android.app.Activity
import android.app.AlertDialog
import android.content.ContentValues
import android.content.Context
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
import androidx.navigation.fragment.navArgs
import com.example.repaircalculator.R
import com.example.repaircalculator.databinding.FragmentNoteBinding
import com.example.repaircalculator.databinding.ModalPriceBinding
import com.example.repaircalculator.ui.screen.modalPrice.ModalBottomSheet
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


        (requireActivity() as MenuHost).addMenuProvider(object : MenuProvider {
            override fun onPrepareMenu(menu: Menu) {
                // Handle for example visibility of menu items
            }

            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.price_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                when (menuItem.itemId) {
                    R.id.price_i -> {
                        val modalBottomSheet = ModalBottomSheet(args.noteId)
                        modalBottomSheet.show(requireActivity().supportFragmentManager, ModalBottomSheet.TAG)
                    }
                }
                return true
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)

        binding.noteListRv.adapter = viewModel.adapter
        binding.materialsListRv.adapter = viewModel.materialsAdapter
//        binding.noteListRv.addItemDecoration(DividerItemDecoration(context,
//            LinearLayout.VERTICAL))

        lifecycleScope.launch {
            binding.noteTitleEt.setText(viewModel.getNote(args.noteId)?.title.toString())
            viewModel.adapter.notes = viewModel.getNoteElements(args.noteId) ?: emptyList()
            viewModel.materialsAdapter.materials = viewModel.getMaterialsElements(args.noteId) ?: emptyList()
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

        binding.addTextBtn.setOnClickListener {
//            viewModel.insertElementText(args.noteId, "")
//
//            lifecycleScope.launch {
//                viewModel.adapter.notes = viewModel.getNoteElements(args.noteId) ?: emptyList()
//            }
            val modalBottomSheet = com.example.repaircalculator.ui.screen.modalMaterialNew.ModalBottomSheet(args.noteId)
            modalBottomSheet.show(requireActivity().supportFragmentManager, ModalBottomSheet.TAG)

        }
    }

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