package com.jmrsa.moviecrudapp.data.remote.dto
import com.google.gson.annotations.SerializedName

data class MovieDto(
    @SerializedName("budget")
    val budget: Int?,
    @SerializedName("cast")
    val cast: List<Cast>?,
    @SerializedName("director")
    val director: Director?,
    @SerializedName("genres")
    val genres: List<String>?,
    @SerializedName("id")
    val id: Int,
    @SerializedName("language")
    val language: String?,
    @SerializedName("overview")
    val overview: String?,
    @SerializedName("posterUrl")
    val posterUrl: String?,
    @SerializedName("rating")
    val rating: Double?,
    @SerializedName("releaseDate")
    val releaseDate: String?,
    @SerializedName("revenue")
    val revenue: Int?,
    @SerializedName("reviews")
    val reviews: Int?,
    @SerializedName("runtime")
    val runtime: Int?,
    @SerializedName("title")
    val title: String?
)