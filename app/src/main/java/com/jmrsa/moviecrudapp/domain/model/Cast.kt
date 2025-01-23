package com.jmrsa.moviecrudapp.domain.model

import com.google.gson.annotations.SerializedName

data class Cast(
    val character: String?,
    val name: String?,
    val pictureUrl: String?
)