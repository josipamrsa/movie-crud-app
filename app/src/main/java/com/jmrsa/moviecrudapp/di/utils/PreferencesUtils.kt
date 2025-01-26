package com.jmrsa.moviecrudapp.di.utils

import android.content.Context
import android.content.SharedPreferences

object PreferencesUtils {
    fun provideSharedPreference(applicationContext: Context) : SharedPreferences =
        applicationContext.getSharedPreferences(
            SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE
        )

    private const val SHARED_PREFERENCES_NAME = "moviecrudprefs"
}