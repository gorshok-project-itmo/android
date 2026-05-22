package com.example.smartpot.data.repository

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

private val Context.dataStore by preferencesDataStore(name = "auth_prefs")

class TokenRepository(private val context: Context) {
    private val TOKEN_KEY = stringPreferencesKey("auth_token")
    private val EMAIL_KEY = stringPreferencesKey("auth_email")

    val tokenFlow: Flow<String?> = context.dataStore.data
        .map { prefs -> prefs[TOKEN_KEY] }

    val emailFlow: Flow<String?> = context.dataStore.data
        .map { prefs -> prefs[EMAIL_KEY] }

    suspend fun saveToken(token: String) {
        context.dataStore.edit { prefs -> prefs[TOKEN_KEY] = token }
    }

    suspend fun clearToken() {
        context.dataStore.edit { prefs -> prefs.remove(TOKEN_KEY) }
        context.dataStore.edit { prefs -> prefs.remove(EMAIL_KEY) }
    }

    suspend fun saveEmail(email: String) {
        context.dataStore.edit { prefs -> prefs[EMAIL_KEY] = email }
    }
}

class TokenProvider(tokenRepository: TokenRepository) {
    @Volatile
    private var token: String? = null

    @Volatile
    private var email: String? = null

    init {
        val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())
        scope.launch {
            tokenRepository.tokenFlow.collect { token = it }
            tokenRepository.emailFlow.collect { email = it }
        }
    }

    fun getToken(): String? = token
    fun getEmail(): String? = email
}
