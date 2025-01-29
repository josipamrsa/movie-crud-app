package com.jmrsa.moviecrudapp.presentation.fragments.details

import com.jmrsa.moviecrudapp.presentation.fragments.base.BaseViewState
import com.jmrsa.moviecrudapp.presentation.models.AppMovie
import com.jmrsa.moviecrudapp.presentation.models.AppUser
import com.jmrsa.moviecrudapp.utils.formatCurrency
import com.jmrsa.moviecrudapp.utils.formatDate
import com.jmrsa.moviecrudapp.utils.formatFullLanguageName
import com.jmrsa.moviecrudapp.utils.formatMinutesToHoursAndMinutes
import com.jmrsa.moviecrudapp.utils.formatRating

interface DetailsContract {
    data class State(
        val movie: AppMovie,
        val user: AppUser? = null,
        val userFavorites: MutableList<AppMovie>? = mutableListOf()
    ) : BaseViewState {
        val formattedRating: Float
            get() = movie.rating?.toFloat() ?: 0f

        val formattedReleaseDate: String
            get() = "${formatDate(movie.releaseDate.orEmpty())} • ${formatMinutesToHoursAndMinutes(movie.runtime?.toInt() ?: 0)}"

        val formattedTitle: String
            get() = "${movie.title} (${movie.releaseDate?.take(4)})"

        val formattedBudget: String
            get() = formatCurrency(movie.budget?.toDouble() ?: 0.00)

        val formattedRevenue: String
            get() = formatCurrency(movie.revenue?.toDouble() ?: 0.00)

        val formattedOriginalLanguage: String
            get() = formatFullLanguageName(movie.language.orEmpty())

        val formattedRatingDecimals: String
            get() = formatRating(movie.rating ?: 0.00)
    }

    sealed interface Effect {
        data object NavigateToHome: Effect
        data object NavigateToSignUp: Effect
    }
}