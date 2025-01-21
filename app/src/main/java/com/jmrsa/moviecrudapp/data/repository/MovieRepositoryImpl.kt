package com.jmrsa.moviecrudapp.data.repository

import com.jmrsa.moviecrudapp.data.remote.api.MoviesApi
import com.jmrsa.moviecrudapp.data.remote.dto.MovieDto
import com.jmrsa.moviecrudapp.domain.repository.MovieRepository

class MovieRepositoryImpl(
    private val api: MoviesApi
) : MovieRepository {
    override suspend fun getMovieList(): List<MovieDto> {
        return api.getMovieList()
    }

    override suspend fun getStaffPicks(): List<MovieDto> {
        return api.getStaffPicks()
    }
}