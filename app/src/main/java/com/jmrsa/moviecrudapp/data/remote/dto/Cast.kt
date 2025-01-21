package com.jmrsa.moviecrudapp.data.remote.dto


import com.google.gson.annotations.SerializedName

data class Cast(
    @SerializedName("character")
    val character: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("pictureUrl")
    val pictureUrl: String?
)