package com.jmrsa.moviecrudapp.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.jmrsa.moviecrudapp.data.db.utils.DataConverter
import com.jmrsa.moviecrudapp.data.remote.dto.Cast
import com.jmrsa.moviecrudapp.data.remote.dto.Director
import com.jmrsa.moviecrudapp.domain.model.helpers.CastList
import com.jmrsa.moviecrudapp.domain.model.helpers.DirectorInfo

@Entity(
    tableName = "movies"
)
data class Movie(
    val budget: Int?,
    //val cast: CastList?,
    //val director: DirectorInfo?,
    val genres: MutableList<String>,
    @PrimaryKey(autoGenerate = false) val id: Int,
    val language: String?,
    val overview: String?,
    val posterUrl: String?,
    val rating: Double?,
    val releaseDate: String?,
    //val revenue: Int?,
    val reviews: Int?,
    val runtime: Int?,
    val title: String?
)