package com.jmrsa.moviecrudapp.domain.use_case

import com.jmrsa.moviecrudapp.domain.base.BaseUseCase
import com.jmrsa.moviecrudapp.domain.model.Movie
import com.jmrsa.moviecrudapp.domain.repository.MovieRepository
import com.jmrsa.moviecrudapp.domain.repository.UserRepository

interface GetMoviesUseCase {
    suspend fun fetchAllMovies() : List<Movie>
    suspend fun fetchStaffPicks() : List<Movie>
}

class GetMoviesUseCaseImpl(
    private val movieRepository: MovieRepository
) : BaseUseCase(), GetMoviesUseCase {

    override suspend fun fetchAllMovies(): List<Movie> {
        return movieRepository.getMovieList()
    }

    override suspend fun fetchStaffPicks(): List<Movie> {
        return movieRepository.getStaffPicks()
    }
}