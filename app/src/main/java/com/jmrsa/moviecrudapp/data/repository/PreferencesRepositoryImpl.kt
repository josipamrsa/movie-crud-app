package com.jmrsa.moviecrudapp.data.repository

import android.content.SharedPreferences
import com.jmrsa.moviecrudapp.domain.repository.PreferencesRepository
import androidx.core.content.edit

class PreferencesRepositoryImpl(
    private val preferences: SharedPreferences
) : PreferencesRepository {
    override var currentUserName: String?
        get() = preferences.getString("CURRENT_ACTIVE_USERNAME", null)
        set(value) {
            preferences.edit {
                putString("CURRENT_ACTIVE_USERNAME", value)
            }
        }

    override var currentUserEmail: String?
        get() = preferences.getString("CURRENT_ACTIVE_EMAIL", null)
        set(value) {
            preferences.edit {
                putString("CURRENT_ACTIVE_EMAIL", value)
            }
        }

    override fun clearSharedPreferences() {
        val editor = preferences.edit()
        editor.clear()
        editor.apply()
    }
}