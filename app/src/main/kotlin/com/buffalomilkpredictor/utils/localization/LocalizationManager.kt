package com.buffalomilkpredictor.utils.localization

import android.content.Context
import android.content.res.Configuration
import android.os.Build
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import timber.log.Timber
import java.util.Locale

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "localization_pref")

class LocalizationManager(private val context: Context) {

    companion object {
        private val LANGUAGE_KEY = stringPreferencesKey("selected_language")
        const val ENGLISH = "en"
        const val HINDI = "hi"
        const val MARATHI = "mr"

        private val supportedLanguages = listOf(ENGLISH, HINDI, MARATHI)
    }

    fun getSelectedLanguageFlow(): Flow<String> {
        return context.dataStore.data.map { preferences ->
            preferences[LANGUAGE_KEY] ?: getSystemLanguage()
        }
    }

    suspend fun setLanguage(languageCode: String) {
        if (languageCode !in supportedLanguages) {
            Timber.w("Unsupported language: $languageCode")
            return
        }

        context.dataStore.edit { preferences ->
            preferences[LANGUAGE_KEY] = languageCode
        }

        applyLocalization(languageCode)
    }

    fun applyLocalization(languageCode: String) {
        try {
            val locale = Locale(languageCode)
            Locale.setDefault(locale)

            val configuration = Configuration(context.resources.configuration)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                configuration.setLocale(locale)
            } else {
                @Suppress("DEPRECATION")
                configuration.locale = locale
            }

            @Suppress("DEPRECATION")
            context.resources.updateConfiguration(
                configuration,
                context.resources.displayMetrics
            )

            Timber.d("Applied localization: $languageCode")
        } catch (e: Exception) {
            Timber.e(e, "Error applying localization")
        }
    }

    private fun getSystemLanguage(): String {
        val locale = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            context.resources.configuration.locales[0]
        } else {
            @Suppress("DEPRECATION")
            context.resources.configuration.locale
        }

        return when (locale.language) {
            HINDI -> HINDI
            MARATHI -> MARATHI
            else -> ENGLISH
        }
    }

    fun getSupportedLanguages(): List<Pair<String, String>> {
        return listOf(
            ENGLISH to "English",
            HINDI to "हिंदी",
            MARATHI to "मराठी"
        )
    }

    fun getLanguageName(languageCode: String): String {
        return when (languageCode) {
            ENGLISH -> "English"
            HINDI -> "हिंदी"
            MARATHI -> "मराठी"
            else -> "Unknown"
        }
    }
}
