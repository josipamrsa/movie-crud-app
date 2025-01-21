package com.jmrsa.moviecrudapp.data.remote.dto

import com.google.gson.annotations.SerializedName

data class Director(
    @SerializedName("name")
    val name: String?,
    @SerializedName("pictureUrl")
    val pictureUrl: String?
)