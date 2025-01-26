package com.jmrsa.moviecrudapp.utils

import android.util.Patterns

fun <T> T?.isNull() = this == null

fun String.isValidEmail() =
    Patterns.EMAIL_ADDRESS.matcher(this).matches()

fun String.isValidPassword() =
    this.length >= 8