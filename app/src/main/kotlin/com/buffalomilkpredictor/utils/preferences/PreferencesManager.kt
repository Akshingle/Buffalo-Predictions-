package com.buffalomilkpredictor.utils.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.preferencesDataStore: DataStore<Preferences> by preferencesDataStore(name = "app_preferences")

class PreferencesManager(private val context: Context) {

    companion object {
        private val DARK_MODE_KEY = booleanPreferencesKey("dark_mode")
        private val LANGUAGE_KEY = stringPreferencesKey("language")
        private val FIRST_LAUNCH_KEY = booleanPreferencesKey("first_launch")
    }

    val darkModeFlow: Flow<Boolean> = context.preferencesDataStore.data.map { preferences ->
        preferences[DARK_MODE_KEY] ?: false
    }

    val languageFlow: Flow<String> = context.preferencesDataStore.data.map { preferences ->
        preferences[LANGUAGE_KEY] ?: "en"
    }

    val isFirstLaunchFlow: Flow<Boolean> = context.preferencesDataStore.data.map { preferences ->
        preferences[FIRST_LAUNCH_KEY] ?: true
    }

    suspend fun setDarkMode(enabled: Boolean) {
        context.preferencesDataStore.edit { preferences ->
            preferences[DARK_MODE_KEY] = enabled
        }
    }

    suspend fun setLanguage(languageCode: String) {
        context.preferencesDataStore.edit { preferences ->
            preferences[LANGUAGE_KEY] = languageCode
        }
    }

    suspend fun setFirstLaunchComplete() {
        context.preferencesDataStore.edit { preferences ->
            preferences[FIRST_LAUNCH_KEY] = false
        }
    }

    suspend fun clearAllPreferences() {
        context.preferencesDataStore.edit { it.clear() }
    }
}
