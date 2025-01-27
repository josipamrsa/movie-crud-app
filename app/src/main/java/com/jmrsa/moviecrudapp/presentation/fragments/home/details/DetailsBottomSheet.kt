package com.jmrsa.moviecrudapp.presentation.fragments.home.details

import com.jmrsa.moviecrudapp.R
import com.jmrsa.moviecrudapp.databinding.LayoutDetailsBinding
import com.jmrsa.moviecrudapp.presentation.shared.dialogs.ModalBottomSheetDialog

class DetailsBottomSheet : ModalBottomSheetDialog<LayoutDetailsBinding>() {
    override fun getContentView(): Int = R.layout.layout_details

    companion object {
        const val BOTTOM_SHEET_TAG = "ModalBottomSheetDetails"
    }
}