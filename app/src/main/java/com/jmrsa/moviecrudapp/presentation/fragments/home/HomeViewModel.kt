package com.jmrsa.moviecrudapp.presentation.fragments.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jmrsa.moviecrudapp.domain.use_case.GetCurrentUserUseCase
import com.jmrsa.moviecrudapp.domain.use_case.GetMoviesUseCase
import com.jmrsa.moviecrudapp.presentation.fragments.base.BaseViewModel
import com.jmrsa.moviecrudapp.presentation.models.toAppMovie
import com.jmrsa.moviecrudapp.presentation.models.toAppUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.withContext

class HomeViewModel(
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val getMoviesUseCase: GetMoviesUseCase
) : BaseViewModel() {
    private val _viewState = MutableLiveData(HomeContract.State())
    val viewState: LiveData<HomeContract.State> = _viewState

    private val _effect: Channel<HomeContract.Effect> = Channel(Channel.BUFFERED)
    val effect = _effect.receiveAsFlow()

    init {
        launchWithProgress {
            val user = withContext(Dispatchers.IO) {
                getCurrentUserUseCase.currentUserEmail()?.let { email ->
                    getCurrentUserUseCase.retrieveCurrentUserFromDatabase(email).toAppUser()
                }
            }

            val picks = getMoviesUseCase.fetchStaffPicks().map { movie -> movie.toAppMovie() }

            _viewState.update {
                it.copy(
                    user = user,
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