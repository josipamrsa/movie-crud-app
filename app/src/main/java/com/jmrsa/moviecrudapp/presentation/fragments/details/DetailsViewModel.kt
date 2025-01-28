package com.jmrsa.moviecrudapp.presentation.fragments.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jmrsa.moviecrudapp.domain.use_case.GetCurrentUserUseCase
import com.jmrsa.moviecrudapp.domain.use_case.GetUserFavoritesUseCase
import com.jmrsa.moviecrudapp.domain.use_case.UpdateUserFavoritesUseCase
import com.jmrsa.moviecrudapp.presentation.fragments.base.BaseViewModel
import com.jmrsa.moviecrudapp.presentation.fragments.home.HomeContract
import com.jmrsa.moviecrudapp.presentation.models.AppMovie
import com.jmrsa.moviecrudapp.presentation.models.AppUser
import com.jmrsa.moviecrudapp.presentation.models.toAppMovie
import com.jmrsa.moviecrudapp.presentation.models.toAppUser
import com.jmrsa.moviecrudapp.presentation.models.toMovie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.withContext

class DetailsViewModel(
    movieDetails: AppMovie,
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val getUserFavoritesUseCase: GetUserFavoritesUseCase,
    private val updateUserFavoritesUseCase: UpdateUserFavoritesUseCase
) : BaseViewModel() {
    private val _viewState = MutableLiveData(DetailsContract.State(movieDetails))
    val viewState: LiveData<DetailsContract.State> = _viewState

    private val _effect: Channel<DetailsContract.Effect> = Channel(Channel.BUFFERED)
    val effect = _effect.receiveAsFlow()

    init {
        launchWithProgress {
            val user = withContext(Dispatchers.IO) { fetchUser() }
            if (user.email.isEmpty()) return@launchWithProgress

            val favorites = fetchFavorites(user)


            _viewState.update {
                it.copy(
                    user = user,
                    userFavorites = favorites
                )
            }
        }
    }

    private suspend fun fetchUser(): AppUser {
        return getCurrentUserUseCase.currentUserEmail()?.let { email ->
            getCurrentUserUseCase.retrieveCurrentUserFromDatabase(email).toAppUser()
        } ?: AppUser("", "")
    }

    private suspend fun fetchFavorites(user: AppUser): MutableList<AppMovie> {
        val userFavorites = getUserFavoritesUseCase.getUserFavorites(user.email).firstOrNull()
        return userFavorites?.firstOrNull()?.favorites?.map { it.toAppMovie(isFavorited = true) }?.toMutableList()
            ?: mutableListOf()
    }

    private fun updateMovieFavorites(
        appMovie: AppMovie,
        movieFavorites: MutableList<AppMovie>?
    ): List<AppMovie> {
        return if (appMovie.isFavorited.not())
            movieFavorites?.plus(appMovie).orEmpty()
        else movieFavorites?.filter { movie -> movie.id != appMovie.id }.orEmpty()
    }

    fun onFavoriteClicked(
        appUser: AppUser?,
        appMovie: AppMovie,
        movieFavorites: MutableList<AppMovie>?
    ) {
        launchIn {
            val newMovieFavorites = updateMovieFavorites(appMovie, movieFavorites)

            appUser?.email?.let { email ->
                updateUserFavoritesUseCase.updateUserFavorites(
                    email, appMovie.toMovie(), newMovieFavorites.map { it.toMovie() })

                getUserFavoritesUseCase.getUserFavorites(email).collectLatest { userFavorites ->
                    val favorites =
                        userFavorites.first().favorites.map { movie -> movie.toAppMovie(isFavorited = true) }
                            .toMutableList()

                    _viewState.update {
                        val updatedMovie = it.movie
                            .copy(isFavorited = favorites
                                .map { fave -> fave.id }
                                .contains(appMovie.id))

                        it.copy(
                            movie = updatedMovie,
                            userFavorites = favorites,
                        )
                    }
                }
            }
        }
    }

    fun onCloseClicked() {
        launchIn {
            _effect.trySend(DetailsContract.Effect.NavigateToHome)
        }
    }
}