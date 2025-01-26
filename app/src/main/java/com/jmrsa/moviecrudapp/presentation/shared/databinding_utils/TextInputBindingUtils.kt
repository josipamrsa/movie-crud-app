package com.jmrsa.moviecrudapp.presentation.shared.databinding_utils

import androidx.annotation.StringRes
import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputLayout

@BindingAdapter("app:errorInfo")
fun TextInputLayout.setErrorMessage(@StringRes errorRes: Int?) {
    error = errorRes?.let { context.getText(it) }
}