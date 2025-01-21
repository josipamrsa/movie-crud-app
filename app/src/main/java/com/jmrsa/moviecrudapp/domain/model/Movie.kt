package com.jmrsa.moviecrudapp.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jmrsa.moviecrudapp.data.remote.dto.Cast
import com.jmrsa.moviecrudapp.data.remote.dto.Director

@Entity(
    tableName = "movies"
)
data class Movie(
    val budget: Int?,
    val cast: List<Cast?>?,
    val director: Director?,
    val genres: List<String?>?,
    @PrimaryKey(autoGenerate = false) val id: Int?,
    val language: String?,
    val overview: String?,
    val posterUrl: String?,
    val rating: Double?,
    val releaseDate: String?,
    val revenue: Int?,
    val reviews: Int?,
    val runtime: Int?,
    val title: String?
)