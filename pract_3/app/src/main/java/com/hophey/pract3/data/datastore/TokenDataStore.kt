package com.hophey.pract3.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map


class TokenDataStore(
    private val context: Context
) {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "auth_prefs")
    companion object {
        private val TOKEN_KEY = stringPreferencesKey("auth_token")
    }

    suspend fun saveToken(token: String) {
        context.dataStore.edit { prefs ->
            prefs[TOKEN_KEY] = token
        }
    }

    suspend fun getToken(): String? {
        return context.dataStore.data
            .map { prefs -> prefs[TOKEN_KEY] }
            .first()
    }

    suspend fun clearToken() {
        context.dataStore.edit { prefs ->
            prefs.remove(TOKEN_KEY)
        }
    }
}