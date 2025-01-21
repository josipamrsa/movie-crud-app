package com.jmrsa.moviecrudapp.di

import com.jmrsa.moviecrudapp.data.repository.MovieRepositoryImpl
import com.jmrsa.moviecrudapp.data.repository.UserRepositoryImpl
import com.jmrsa.moviecrudapp.di.utils.DatabaseUtils
import com.jmrsa.moviecrudapp.di.utils.NetworkUtils
import com.jmrsa.moviecrudapp.domain.repository.UserRepository
import com.jmrsa.moviecrudapp.domain.repository.MovieRepository
import org.koin.core.module.dsl.singleOf
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
        single { provideUserRepository(get()) }
    }

    singleOf(::MovieRepositoryImpl).bind<MovieRepository>()
    singleOf(::UserRepositoryImpl).bind<UserRepository>()
}