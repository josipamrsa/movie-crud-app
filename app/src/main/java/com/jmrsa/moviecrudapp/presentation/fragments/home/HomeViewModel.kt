package com.jmrsa.moviecrudapp.presentation.fragments.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jmrsa.moviecrudapp.domain.use_case.GetCurrentUserUseCase
import com.jmrsa.moviecrudapp.domain.use_case.GetMoviesUseCase
import com.jmrsa.moviecrudapp.domain.use_case.GetUserFavoritesUseCase
import com.jmrsa.moviecrudapp.presentation.fragments.base.BaseViewModel
import com.jmrsa.moviecrudapp.presentation.models.AppMovie
import com.jmrsa.moviecrudapp.presentation.models.AppUser
import com.jmrsa.moviecrudapp.presentation.models.toAppMovie
import com.jmrsa.moviecrudapp.presentation.models.toAppUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.withContext

class HomeViewModel(
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val getUserFavoritesUseCase: GetUserFavoritesUseCase,
    private val getMoviesUseCase: GetMoviesUseCase
) : BaseViewModel() {
    private val _viewState = MutableLiveData(HomeContract.State())
    val viewState: LiveData<HomeContract.State> = _viewState

    private val _effect: Channel<HomeContract.Effect> = Channel(Channel.BUFFERED)
    val effect = _effect.receiveAsFlow()

    private suspend fun fetchFavorites(user: AppUser) : MutableList<AppMovie> {
        var favorites = mutableListOf<AppMovie>()

        user.email.let {
            getUserFavoritesUseCase.getUserFavorites(it).onEach { userFavorites ->
                favorites = userFavorites.first().favorites.map { movie -> movie.toAppMovie() }.toMutableList()
            }
        }

        return favorites
    }

    private suspend fun fetchStaffPicks(): List<AppMovie> {
        return getMoviesUseCase.fetchStaffPicks().map { movie -> movie.toAppMovie() }
    }

    init {
        launchWithProgress {
            val user = withContext(Dispatchers.IO) {
                getCurrentUserUseCase.currentUserEmail()?.let { email ->
                    getCurrentUserUseCase.retrieveCurrentUserFromDatabase(email).toAppUser()
                }
            }

            val favorites = user?.let { fetchFavorites(it) } ?: mutableListOf()

            val picks = fetchStaffPicks().map { pick ->
                pick.copy(
                    isFavorited = favorites.contains(pick)
                )
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

    fun onSearchClicked() {
        launchIn {
            _effect.trySend(HomeContract.Effect.NavigateToSearch)
        }
    }
}