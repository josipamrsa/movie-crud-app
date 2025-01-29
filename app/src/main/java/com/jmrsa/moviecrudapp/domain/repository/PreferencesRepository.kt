package com.jmrsa.moviecrudapp.domain.repository

interface PreferencesRepository {
    var currentUserName: String?
    var currentUserEmail: String?
    fun clearSharedPreferences()
}