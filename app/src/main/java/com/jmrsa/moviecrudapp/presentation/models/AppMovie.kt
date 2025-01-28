package com.jmrsa.moviecrudapp.presentation.models

import android.os.Parcelable
import com.jmrsa.moviecrudapp.data.remote.dto.MovieDto
import com.jmrsa.moviecrudapp.domain.model.Movie
import kotlinx.parcelize.Parcelize

@Parcelize
data class AppMovie(
    val budget: Int?,
    val genres: MutableList<String>?,
    val id: Int,
    val language: String?,
    val overview: String?,
    val posterUrl: String?,
    val rating: Double?,
    val releaseDate: String?,
    val revenue: Int?,
    val reviews: Int?,
    val runtime: Int?,
    val title: String?,
    val isFavorited: Boolean = false
) : Parcelable

fun Movie.toAppMovie(isFavorited: Boolean = false) = AppMovie(
    budget = budget,
    genres = genres,
    id = id,
    language = language,
    overview = overview,
    posterUrl = posterUrl,
    rating = rating,
    releaseDate = releaseDate,
    revenue = revenue,
    reviews = reviews,
    runtime = runtime,
    title = title,
    isFavorited = isFavorited
)

fun MovieDto.toAppMovie(isFavorited: Boolean = false) = AppMovie(
    budget = budget,
    genres = genres?.toMutableList(),
    id = id,
    language = language,
    overview = overview,
    posterUrl = posterUrl,
    rating = rating,
    releaseDate = releaseDate,
    revenue = revenue,
    reviews = reviews,
    runtime = runtime,
    title = title,
    isFavorited = isFavorited
)

fun AppMovie.toMovie() = Movie(
    budget = budget,
    genres = genres,
    id = id,
    language = language,
    overview = overview,
    posterUrl = posterUrl,
    rating = rating,
    releaseDate = releaseDate,
    revenue = revenue,
    reviews = reviews,
    runtime = runtime,
    title = title
)

