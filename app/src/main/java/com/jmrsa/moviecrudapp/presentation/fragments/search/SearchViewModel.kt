package com.jmrsa.moviecrudapp.presentation.fragments.search

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

class SearchViewModel(
    private val getMoviesUseCase: GetMoviesUseCase,
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val getUserFavoritesUseCase: GetUserFavoritesUseCase,
    private val updateUserFavoritesUseCase: UpdateUserFavoritesUseCase
) : BaseViewModel() {
    private val _viewState = MutableLiveData(SearchContract.State())
    val viewState: LiveData<SearchContract.State> = _viewState

    private val _effect: Channel<SearchContract.Effect> = Channel(Channel.BUFFERED)
    val effect = _effect.receiveAsFlow()

    init {
        launchWithProgress {
            val user = withContext(Dispatchers.IO) { fetchUser() }
            if (user.email.isEmpty()) {
                getCurrentUserUseCase.clearPreferences()
                _effect.trySend(SearchContract.Effect.NavigateToSignUp)
                return@launchWithProgress
            }

            val favorites = fetchFavorites(user)

            val movies = fetchAllMovies().map { pick ->
                pick.copy(isFavorited = labelMoviesIfFavorited(pick, favorites))
            }

            _viewState.update {
                it.copy(
                    user = user,
                    userFavorites = favorites,
                    allMovies = movies
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
        //REVIEW: would have been nice to create flow for movies and favorites and combine them properly
        // this does not react to favorite changes by itself
        val userFavorites = getUserFavoritesUseCase.getUserFavorites(user.email).firstOrNull()
        return userFavorites?.firstOrNull()?.favorites?.map { it.toAppMovie(isFavorited = true) }?.toMutableList()
            ?: mutableListOf()
    }

    private suspend fun fetchAllMovies(): List<AppMovie> {
        return getMoviesUseCase.fetchAllMovies().map { movie -> movie.toAppMovie() }
    }

    private fun labelMoviesIfFavorited(pick: AppMovie, favorites: MutableList<AppMovie>): Boolean {
        return favorites.map { it.id }.contains(pick.id)
    }

    private fun updateMovieFavorites(appMovie: AppMovie, movieFavorites: MutableList<AppMovie>?) : List<AppMovie> {
        return if (appMovie.isFavorited.not())
            movieFavorites?.plus(appMovie).orEmpty()
        else movieFavorites?.filter { movie -> movie.id != appMovie.id }.orEmpty()
    }

    fun onGoBackHome() {
        launchIn {
            _effect.trySend(SearchContract.Effect.NavigateToHome)
        }
    }

    fun onSearchValueChanged(searchQuery: String) {
        //REVIEW: instead of directly performing the search it would have been better to
        // create a separate SharedFlow for the search query, which you could then subscribe to
        // and debounce in the viewmodel
        launchIn {
            //REVIEW: this will perform a network request every time a character is changed
            val updatedMovieList = fetchAllMovies()
                //REVIEW: filter should have used a ignoreCase = true
                .filter { it.title?.contains(searchQuery) == true }

            _viewState.update {
                it.copy(allMovies = updatedMovieList)
            }
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

                //REVIEW: same issue as in the [HomeViewModel]
                getUserFavoritesUseCase.getUserFavorites(email).collectLatest { userFavorites ->
                    val favorites =
                        userFavorites.first().favorites.map { movie -> movie.toAppMovie(isFavorited = true) }
                            .toMutableList()

                    val movies = fetchAllMovies().map { movie ->
                        movie.copy(isFavorited = labelMoviesIfFavorited(movie, favorites))
                    }

                    _viewState.update {
                        it.copy(
                            userFavorites = favorites,
                            allMovies = movies
                        )
                    }
                }
            }
        }
    }
}