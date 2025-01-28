package com.jmrsa.moviecrudapp.presentation.models

import android.os.Parcelable
import com.jmrsa.moviecrudapp.domain.model.User
import kotlinx.parcelize.Parcelize

@Parcelize
data class AppUser (
    val userName: String,
    val email: String,
    val imageUri: String? = ""
) : Parcelable


fun User.toAppUser() = AppUser(
    userName = userName,
    email = email,
    imageUri = imageUri
)