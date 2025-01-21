package com.jmrsa.moviecrudapp.data.remote.api

import com.jmrsa.moviecrudapp.data.remote.NetworkContract
import com.jmrsa.moviecrudapp.data.remote.dto.MovieDto
import retrofit2.http.GET

interface MoviesApi {
    @GET(NetworkContract.MOVIE_API)
    suspend fun getMovieList() : List<MovieDto>

    @GET(NetworkContract.STAFF_PICKS_API)
    suspend fun getStaffPicks(): List<MovieDto>
}