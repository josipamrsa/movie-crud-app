package com.jmrsa.moviecrudapp.ui.shared.dialogs

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

abstract class ModalBottomSheetDialog<T: ViewDataBinding> : BottomSheetDialogFragment() {
    private lateinit var binding: T

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, getContentView(), container, false)
        return binding.root
    }

    abstract fun getContentView(): Int

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        dialog?.setOnShowListener { dialogInterface ->
            val bottomSheetDialog = dialogInterface as BottomSheetDialog
            val bottomSheet = bottomSheetDialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)

            bottomSheet?.let {
                it.layoutParams = it.layoutParams.apply {
                    height = ViewGroup.LayoutParams.MATCH_PARENT
                }

                val sheetBehavior = BottomSheetBehavior.from(it)
                sheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
                sheetBehavior.skipCollapsed = true
            }
        }

        return super.onCreateDialog(savedInstanceState)
    }

    companion object {
        const val BOTTOM_SHEET_TAG = "ModalBottomSheetDialog"
    }
}