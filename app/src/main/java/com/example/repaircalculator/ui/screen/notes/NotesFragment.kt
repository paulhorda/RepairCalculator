package com.example.repaircalculator.ui.screen.notes

import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.repaircalculator.R
import com.example.repaircalculator.data.entity.Note
import com.example.repaircalculator.data.entity.NoteType
import com.example.repaircalculator.databinding.FragmentNotesBinding
import com.yalantis.ucrop.UCrop
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.io.File


@AndroidEntryPoint
class NotesFragment : Fragment() {

    private val REQUEST_CODE: Int = 101
    private lateinit var binding: FragmentNotesBinding

    private val args: NotesFragmentArgs by navArgs()

    private val viewModel: NotesViewModel by viewModels()

    private val SAMPLE_CROPPED_IMAGE_NAME = "SampleCropImage"

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

        val permissions =
            arrayOf(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
            )
        val PERMISSION_REQUEST_CODE = 101
        requestPermissions(permissions, PERMISSION_REQUEST_CODE)

        viewModel.setNotes(args.roomId)

        viewModel.adapter.noteCallback = {
            Log.d("jsldkfjsd", it.toString())
            val action = NotesFragmentDirections.actionNotesFragmentToNoteFragment(it.id)
            view.findNavController().navigate(action)
        }

        binding.newNoteEfab.setOnClickListener{
            viewModel.insertNote(Note(0, args.roomId, "", NoteType.NOTE, 1, System.currentTimeMillis()))
            viewModel.insertNote(Note(0, args.roomId, "", NoteType.TABLE, 1, System.currentTimeMillis()))
            viewModel.setNotes(args.roomId)
        }
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onPrepareMenu(menu: Menu) {
                // Handle for example visibility of menu items
            }

            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.paint_room_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                when (menuItem.itemId) {
                    R.id.log_out -> {
                        lifecycleScope.launch {
                            val room = viewModel.getRoom(args.roomId)

                            if(room!=null) {
                                val project = viewModel.getProject(room.projectId)
//                                UCrop.of(Uri.parse(room.image),
//                                    Uri.fromFile(File(activity!!.cacheDir,
//                                        SAMPLE_CROPPED_IMAGE_NAME)))
//                                    .withAspectRatio(16f, 9f)
//                                    .withMaxResultSize(1000, 2000)
//                                    .start(requireActivity());
                                if (project != null) {
                                    var destinationFileName: String = SAMPLE_CROPPED_IMAGE_NAME
                                    destinationFileName += ".png"

                                    var uCrop = UCrop.of(Uri.parse(project.image),
                                        Uri.fromFile(File(activity!!.cacheDir, destinationFileName)))

                                    uCrop = uCrop.withAspectRatio(1f, 1f)

                                    val options = UCrop.Options()
                                    options.setFreeStyleCropEnabled(true)
                                    options.setCompressionFormat(Bitmap.CompressFormat.PNG)
                                    options.setCompressionQuality(90)

                                    uCrop = uCrop.withOptions(options)
                                    uCrop.start(requireContext(), this@NotesFragment)
                                }

//                                if (requestMode == com.yalantis.ucrop.sample.SampleActivity.REQUEST_SELECT_PICTURE_FOR_FRAGMENT) {       //if build variant = fragment
//                                    setupFragment(uCrop)
//                                } else {                                                        // else start uCrop Activity
//
//                                }
//                            if(room!=null)
//                            performCrop(Uri.parse(room.image))
                            }
                        }
//                        val intent = Intent(requireContext(), com.example.repaircalculator.ui.screen.planPaint.MainActivity::class.java)
//                        intent.putExtra("id", args.roomId)
//                        startActivity(intent)
                    }
                    R.id.image_watch ->{
                        lifecycleScope.launch {

                            val intent = Intent()
                            intent.action = Intent.ACTION_VIEW
                            val projectId = activity?.intent?.getIntExtra("KEY_PROJECT", 0) ?: 0

                            context?.getExternalFilesDir("images/")
//                            val file = File()
//                            val photoURI = FileProvider.getUriForFile(
//                                context!!,
//                                context!!.applicationContext.packageName + ".provider",
//                                file
//                            )

                            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

                            intent.setDataAndType(
                                Uri.parse(viewModel.getProject(projectId)?.image ?: ""),
                                "image/*"
                            )
                            startActivity(intent)

                        }

                    }

                }
                return true
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)

    }

    private fun setLayoutManager() {
        val dividerItemDecoration = DividerItemDecoration(requireContext(), LinearLayout.VERTICAL)

        binding.notesListRv.adapter = viewModel.adapter
//        binding.roomsRv.addItemDecoration(dividerItemDecoration)
    }

    val PIC_CROP = 1

    private fun performCrop(picUri: Uri) {
        try {
            val cropIntent = Intent("com.android.camera.action.CROP")
            // indicate image type and Uri
            cropIntent.setDataAndType(picUri, "image/*")
            // set crop properties here
            cropIntent.putExtra("crop", true)
            // indicate aspect of desired crop
            cropIntent.putExtra("aspectX", 1)
            cropIntent.putExtra("aspectY", 1)
            // indicate output X and Y
            cropIntent.putExtra("outputX", 128)
            cropIntent.putExtra("outputY", 128)
            // retrieve data on return
            cropIntent.putExtra("return-data", true)
            // start the activity - we handle returning in onActivityResult
            startActivityForResult(cropIntent, PIC_CROP)
        } // respond to users whose devices do not support the crop action
        catch (anfe: ActivityNotFoundException) {
            // display an error message
            val errorMessage = "Whoops - your device doesn't support the crop action!"
            val toast: Toast = Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT)
            toast.show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == RESULT_OK && requestCode == UCrop.REQUEST_CROP) {
            val resultUri = UCrop.getOutput(data!!)
            Log.d("lfsjkdljlskf", resultUri.toString())
            viewModel.updateRoomImage(args.roomId, resultUri.toString())
        } else if (resultCode == UCrop.RESULT_ERROR) {
            val cropError = UCrop.getError(data!!)
            Log.d("lfsjkdljlskf", cropError.toString())
        }
        else{
            Log.d("lfsjkdljlskf", "else")
        }
    }

}
