package com.jmrsa.moviecrudapp.presentation.shared.dialogs

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

abstract class ModalBottomSheetDialogFragment<VB : ViewDataBinding> : BottomSheetDialogFragment() {
    private var _binding: VB? = null
    protected val binding
        get() = _binding!!

    abstract fun handleBindings(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): VB

    abstract fun initView(binding: VB)

    //abstract fun navigateBack()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = handleBindings(inflater = inflater, container = container)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(binding)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val bottomSheetDialog = BottomSheetDialog(requireContext())

        bottomSheetDialog.setOnShowListener { dialogInterface ->
            val sheetDialog = dialogInterface as BottomSheetDialog

            val bottomSheet =
                sheetDialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)

            bottomSheet?.let {
                it.layoutParams = it.layoutParams.apply {
                    height = ViewGroup.LayoutParams.MATCH_PARENT
                }

                val sheetBehavior = BottomSheetBehavior.from(it)
                sheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
                sheetBehavior.skipCollapsed = true
                sheetBehavior.isDraggable = false

                /*sheetBehavior.addBottomSheetCallback(object :
                    BottomSheetBehavior.BottomSheetCallback() {
                    override fun onStateChanged(bottomSheet: View, newState: Int) {
                        if (newState == BottomSheetBehavior.STATE_COLLAPSED || newState == BottomSheetBehavior.STATE_HIDDEN) {
                            navigateBack()
                        }
                    }

                    override fun onSlide(bottomSheet: View, slideOffset: Float) {
                        if (slideOffset < 0)
                            navigateBack()
                    }
                })*/
            }
        }

        return bottomSheetDialog
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}