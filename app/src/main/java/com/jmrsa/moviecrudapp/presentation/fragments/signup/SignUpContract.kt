package com.jmrsa.moviecrudapp.presentation.fragments.signup

import androidx.annotation.StringRes
import com.jmrsa.moviecrudapp.presentation.fragments.base.BaseViewState

interface SignUpContract {
    data class State(
        val imageUri: String? = "",
        @StringRes val nameError: Int? = null,
        @StringRes val emailError: Int? = null,
        @StringRes val passwordError: Int? = null,
        @StringRes val confirmError: Int? = null
    ) : BaseViewState

    sealed interface Effect {
        data object NavigateToHome: Effect
    }
}

