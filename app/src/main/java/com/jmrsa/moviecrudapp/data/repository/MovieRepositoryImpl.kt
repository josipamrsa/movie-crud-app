package com.jmrsa.moviecrudapp.data.repository

import com.jmrsa.moviecrudapp.data.db.UserDao
import com.jmrsa.moviecrudapp.data.remote.api.MoviesApi
import com.jmrsa.moviecrudapp.data.remote.converters.toMovie
import com.jmrsa.moviecrudapp.data.remote.dto.MovieDto
import com.jmrsa.moviecrudapp.domain.model.Movie
import com.jmrsa.moviecrudapp.domain.repository.MovieRepository

class MovieRepositoryImpl(
    private val api: MoviesApi,
    private val dao: UserDao
) : MovieRepository {
    override suspend fun getMovieList(): List<Movie> {
        return api.getMovieList().map { movie -> movie.toMovie() }
    }

    override suspend fun getStaffPicks(): List<Movie> {
        return api.getStaffPicks().map { movie -> movie.toMovie() }
    }
}