package com.jmrsa.moviecrudapp.presentation.fragments.signup

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jmrsa.moviecrudapp.R
import com.jmrsa.moviecrudapp.domain.repository.PreferencesRepository
import com.jmrsa.moviecrudapp.domain.use_case.RegisterUserUseCase
import com.jmrsa.moviecrudapp.presentation.fragments.base.BaseViewModel
import com.jmrsa.moviecrudapp.presentation.models.toAppUser
import com.jmrsa.moviecrudapp.utils.isNull
import com.jmrsa.moviecrudapp.utils.isValidEmail
import com.jmrsa.moviecrudapp.utils.isValidPassword
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.withContext

class SignUpViewModel(
    private val registerUserUseCase: RegisterUserUseCase,
    private val preferencesRepository: PreferencesRepository
    //getCurrentUserUseCase: GetCurrentUserUseCase
) : BaseViewModel() {

    private val _viewState = MutableLiveData(SignUpContract.State())
    val viewState: LiveData<SignUpContract.State> = _viewState

    private val _effect: Channel<SignUpContract.Effect> = Channel()
    val effect = _effect.receiveAsFlow()

    init {
        preferencesRepository.currentUserEmail.let {
            _effect.trySend(SignUpContract.Effect.NavigateToHome)
        }
    }

    fun onSignUpClicked(name: String, email: String, password: String, confirmPassword: String) {
        val isDataValid = checkFormData(name, email, password, confirmPassword)
        Log.d("SignUpViewModel", "Vibecheck: $isDataValid")

        if (isDataValid.not()) return

        launchWithProgress {
            withContext(Dispatchers.IO) {
                val user = registerUserUseCase.registerUser(name, email, password)?.toAppUser()
                preferencesRepository.currentUserName = user?.userName
                preferencesRepository.currentUserEmail = user?.email
                _effect.trySend(SignUpContract.Effect.NavigateToHome)
            }
        }
    }

    private fun checkFormData(
        name: String,
        email: String,
        password: String,
        confirmPassword: String
    ): Boolean {
        val nameErrorRes = when {
            name.isEmpty() -> R.string.movie_crud_error_name_empty_label
            else -> null
        }

        val emailErrorRes = when {
            email.isEmpty() -> R.string.movie_crud_error_email_empty_label
            email.isValidEmail().not() -> R.string.movie_crud_error_email_invalid_label
            else -> null
        }

        val passwordErrorRes = when {
            password.isEmpty() -> R.string.movie_crud_error_password_empty_label
            password.isValidPassword().not() -> R.string.movie_crud_error_password_short_label
            else -> null
        }

        val confirmPasswordErrorRes = when {
            password != confirmPassword -> R.string.movie_crud_error_confirm_label
            else -> null
        }

        _viewState.update {
            it.copy(
                nameError = nameErrorRes,
                emailError = emailErrorRes,
                passwordError = passwordErrorRes,
                confirmError = confirmPasswordErrorRes
            )
        }

        return nameErrorRes.isNull() &&
                emailErrorRes.isNull() &&
                passwordErrorRes.isNull() &&
                confirmPasswordErrorRes.isNull()
    }
}