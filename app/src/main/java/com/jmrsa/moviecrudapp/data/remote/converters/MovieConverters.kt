package com.jmrsa.moviecrudapp.data.remote.converters

import com.jmrsa.moviecrudapp.data.remote.dto.MovieDto
import com.jmrsa.moviecrudapp.domain.model.Movie

fun MovieDto.toMovie() = Movie(
    budget = budget,
    genres = genres?.toMutableList(),
    id = id,
    language = language,
    overview = overview,
    posterUrl = posterUrl,
    rating = rating,
    releaseDate = releaseDate,
    reviews = reviews,
    runtime = runtime,
    revenue = revenue,
    title = title
)

