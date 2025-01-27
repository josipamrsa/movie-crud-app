package com.jmrsa.moviecrudapp.domain.repository

import com.jmrsa.moviecrudapp.domain.model.Movie

interface MovieRepository {
    suspend fun getMovieList(): List<Movie>
    suspend fun getStaffPicks(): List<Movie>
}

