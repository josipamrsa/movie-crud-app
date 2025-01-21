package com.jmrsa.moviecrudapp.domain.repository

import com.jmrsa.moviecrudapp.data.remote.dto.MovieDto

interface MovieRepository {
    suspend fun getMovieList(): List<MovieDto>
    suspend fun getStaffPicks(): List<MovieDto>
}