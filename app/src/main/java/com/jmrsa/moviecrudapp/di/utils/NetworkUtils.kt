package com.jmrsa.moviecrudapp.di.utils

import com.jmrsa.moviecrudapp.data.remote.NetworkContract
import com.jmrsa.moviecrudapp.data.remote.api.MoviesApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkUtils {
    fun provideHttpClient(): OkHttpClient =
        OkHttpClient
            .Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()


    fun provideConverterFactory(): GsonConverterFactory =
        GsonConverterFactory.create()

    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl(NetworkContract.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(gsonConverterFactory)
            .build()

    fun provideService(retrofit: Retrofit): MoviesApi =
        retrofit.create(MoviesApi::class.java)
}