package dev.kigya.mindplex.core.util

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

interface JwtProvider {
    suspend fun getToken(): String?
}

class MindplexJwtProvider(
    private val dataStore: DataStore<Preferences>,
    private val dispatcher: CoroutineDispatcher,
) : JwtProvider {

    override suspend fun getToken(): String? = withContext(dispatcher) {
        dataStore.getJwtToken()
    }
}

suspend fun DataStore<Preferences>.getJwtToken(): String? = this.data
    .map { preferences -> preferences[stringPreferencesKey(MINDPLEX_JWT_KEY)] }
    .firstOrNull()

private const val MINDPLEX_JWT_KEY = "mindplex_jwt"
