package com.jmrsa.moviecrudapp.presentation.fragments.home

import com.jmrsa.moviecrudapp.presentation.fragments.base.BaseViewState
import com.jmrsa.moviecrudapp.presentation.models.AppMovie
import com.jmrsa.moviecrudapp.presentation.models.AppUser

interface HomeContract {
    data class State(
        val user: AppUser? = null,
        val userFavorites: List<AppMovie>? = mutableListOf(),
        val staffPicks: List<AppMovie>? = mutableListOf()
    ) : BaseViewState

    sealed interface Effect {
        data object OpenDetails: Effect
        data object NavigateToSearch: Effect
    }
}