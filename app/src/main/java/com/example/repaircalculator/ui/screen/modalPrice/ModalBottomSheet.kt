package com.example.repaircalculator.ui.screen.modalPrice

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.repaircalculator.R
import com.example.repaircalculator.databinding.NotePriceBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ModalBottomSheet(private val noteId: Int) : BottomSheetDialogFragment() {

    private lateinit var binding: NotePriceBinding

    private val viewModel: ModalBottomSheetViewModel by viewModels()

    private lateinit var materialAlertDialogBuilder: MaterialAlertDialogBuilder
    private lateinit var customAlertDialogView: View

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = NotePriceBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.priceListRv.adapter = viewModel.adapter

        viewModel.setPricesForNote(noteId)

        binding.addNewPriceBtn.setOnClickListener {
            materialAlertDialogBuilder = MaterialAlertDialogBuilder(requireContext())

            customAlertDialogView = LayoutInflater.from(requireContext())
                .inflate(R.layout.modal_price, null, false)

            materialAlertDialogBuilder.setView(customAlertDialogView)
                .setTitle("New price")
                .setPositiveButton("OK") { _: DialogInterface, _: Int ->
                    viewModel.insertPrice(noteId,
                        customAlertDialogView.findViewById<TextInputLayout>(R.id.title_til).editText?.text.toString(),
                        customAlertDialogView.findViewById<TextInputLayout>(R.id.price_til).editText?.text.toString().toIntOrNull() ?: 0)
                }
                .show()

//
//            MaterialAlertDialogBuilder(requireContext())
//                .setView(R.layout.modal_price)
//                .setPositiveButton("OK") { _: DialogInterface, _: Int ->
////                    viewModel.insertPrice(noteId, bindingPrice.titleTil.editText?.text.toString(),
////                        bindingPrice.priceTil.editText?.text.toString().toIntOrNull()?:0)
//                }
//                .show()
        }
    }

    companion object {
        const val TAG = "ModalBottomSheet"
    }
}
