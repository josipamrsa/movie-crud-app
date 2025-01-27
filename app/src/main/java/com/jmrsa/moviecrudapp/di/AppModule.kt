package com.jmrsa.moviecrudapp.di

import android.content.SharedPreferences
import com.jmrsa.moviecrudapp.data.repository.MovieRepositoryImpl
import com.jmrsa.moviecrudapp.data.repository.PreferencesRepositoryImpl
import com.jmrsa.moviecrudapp.data.repository.UserRepositoryImpl
import com.jmrsa.moviecrudapp.di.utils.DatabaseUtils
import com.jmrsa.moviecrudapp.di.utils.NetworkUtils
import com.jmrsa.moviecrudapp.di.utils.PreferencesUtils
import com.jmrsa.moviecrudapp.domain.repository.UserRepository
import com.jmrsa.moviecrudapp.domain.repository.MovieRepository
import com.jmrsa.moviecrudapp.domain.repository.PreferencesRepository
import com.jmrsa.moviecrudapp.domain.use_case.GetCurrentUserUseCase
import com.jmrsa.moviecrudapp.domain.use_case.GetCurrentUserUseCaseImpl
import com.jmrsa.moviecrudapp.domain.use_case.RegisterUserUseCase
import com.jmrsa.moviecrudapp.domain.use_case.RegisterUserUseCaseImpl
import com.jmrsa.moviecrudapp.presentation.fragments.signup.SignUpViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.bind
import org.koin.dsl.module

val appModule = module {
    NetworkUtils.apply {
        single { provideHttpClient() }
        single { provideConverterFactory() }
        single { provideRetrofit(get(), get()) }
        single { provideService(get()) }
    }

    DatabaseUtils.apply {
        single { provideDatabase(get()) }
        single { provideUserDao(get()) }
        single { provideUserRepository(get()) }
    }

    PreferencesUtils.apply {
        single<SharedPreferences> {
            provideSharedPreference(get())
        }
    }

    single { MovieRepositoryImpl(get()) }.bind<MovieRepository>()
    single { UserRepositoryImpl(get()) }.bind<UserRepository>()
    single { PreferencesRepositoryImpl(get()) }.bind<PreferencesRepository>()

    factory<RegisterUserUseCase> { RegisterUserUseCaseImpl(get(), get()) }
    factory<GetCurrentUserUseCase> { GetCurrentUserUseCaseImpl(get()) }

    viewModel { SignUpViewModel(get(), get()) }
}