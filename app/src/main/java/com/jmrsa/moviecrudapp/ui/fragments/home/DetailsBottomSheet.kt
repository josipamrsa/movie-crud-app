package com.jmrsa.moviecrudapp.ui.fragments.home

import com.jmrsa.moviecrudapp.R
import com.jmrsa.moviecrudapp.databinding.LayoutDetailsBinding
import com.jmrsa.moviecrudapp.ui.shared.dialogs.ModalBottomSheetDialog

class DetailsBottomSheet : ModalBottomSheetDialog<LayoutDetailsBinding>() {
    override fun getContentView(): Int = R.layout.layout_details

    companion object {
        const val BOTTOM_SHEET_TAG = "ModalBottomSheetDetails"
    }
}