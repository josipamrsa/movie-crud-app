package com.jmrsa.moviecrudapp.presentation.fragments.search

import com.jmrsa.moviecrudapp.presentation.fragments.base.BaseViewState
import com.jmrsa.moviecrudapp.presentation.models.AppMovie
import com.jmrsa.moviecrudapp.presentation.models.AppUser

interface SearchContract {
    data class State(
        val user: AppUser? = null,
        val userFavorites: MutableList<AppMovie>? = mutableListOf(),
        val allMovies: List<AppMovie>? = mutableListOf()
    ): BaseViewState

    sealed interface Effect {
        data object NavigateToHome: Effect
        data object NavigateToSignUp: Effect
    }
}