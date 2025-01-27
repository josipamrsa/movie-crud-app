package com.jmrsa.moviecrudapp.domain.use_case

import com.jmrsa.moviecrudapp.domain.base.BaseUseCase
import com.jmrsa.moviecrudapp.domain.model.Movie
import com.jmrsa.moviecrudapp.domain.repository.MovieRepository

interface GetMoviesUseCase {
    suspend fun fetchFavoriteMovies() : List<Movie>
    suspend fun fetchStaffPicks() : List<Movie>
}

class GetMoviesUseCaseImpl(
    private val movieRepository: MovieRepository
) : BaseUseCase(), GetMoviesUseCase {

    override suspend fun fetchFavoriteMovies(): List<Movie> {
        TODO("Not yet implemented")
    }

    override suspend fun fetchStaffPicks(): List<Movie> {
        return movieRepository.getStaffPicks()
    }
}