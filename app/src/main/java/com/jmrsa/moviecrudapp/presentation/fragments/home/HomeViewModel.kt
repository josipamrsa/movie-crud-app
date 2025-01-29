package com.jmrsa.moviecrudapp.presentation.fragments.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jmrsa.moviecrudapp.domain.use_case.GetCurrentUserUseCase
import com.jmrsa.moviecrudapp.domain.use_case.GetMoviesUseCase
import com.jmrsa.moviecrudapp.domain.use_case.GetUserFavoritesUseCase
import com.jmrsa.moviecrudapp.domain.use_case.UpdateUserFavoritesUseCase
import com.jmrsa.moviecrudapp.presentation.fragments.base.BaseViewModel
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

class HomeViewModel(
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val getUserFavoritesUseCase: GetUserFavoritesUseCase,
    private val getMoviesUseCase: GetMoviesUseCase,
    private val updateUserFavoritesUseCase: UpdateUserFavoritesUseCase
) : BaseViewModel() {
    private val _viewState = MutableLiveData(HomeContract.State())
    val viewState: LiveData<HomeContract.State> = _viewState

    private val _effect: Channel<HomeContract.Effect> = Channel(Channel.BUFFERED)
    val effect = _effect.receiveAsFlow()

    init {
        launchWithProgress {
            val user = withContext(Dispatchers.IO) { fetchUser() }

            if (user.email.isEmpty()) {
                getCurrentUserUseCase.clearPreferences()
                _effect.trySend(HomeContract.Effect.NavigateToSignUp)
                return@launchWithProgress
            }

            val favorites = fetchFavorites(user)

            val picks = fetchStaffPicks().map { pick ->
                pick.copy(isFavorited = labelPicksIfFavorited(pick, favorites))
            }

            _viewState.update {
                it.copy(
                    user = user,
                    userFavorites = favorites,
                    staffPicks = picks
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

    private suspend fun fetchStaffPicks(): List<AppMovie> {
        return getMoviesUseCase.fetchStaffPicks().map { movie -> movie.toAppMovie() }
    }

    private fun labelPicksIfFavorited(pick: AppMovie, favorites: MutableList<AppMovie>): Boolean {
        return favorites.map { it.id }.contains(pick.id)
    }

    private fun updateMovieFavorites(appMovie: AppMovie, movieFavorites: MutableList<AppMovie>?) : List<AppMovie> {
        return if (appMovie.isFavorited.not())
            movieFavorites?.plus(appMovie).orEmpty()
        else movieFavorites?.filter { movie -> movie.id != appMovie.id }.orEmpty()
    }

    fun onSearchClicked() {
        launchIn {
            _effect.trySend(HomeContract.Effect.NavigateToSearch)
        }
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

                    val picks = _viewState.value?.staffPicks?.map { pick ->
                        pick.copy(isFavorited = labelPicksIfFavorited(pick, favorites))
                    }

                    _viewState.update {
                        it.copy(
                            userFavorites = favorites,
                            staffPicks = picks
                        )
                    }
                }
            }
        }
    }
}