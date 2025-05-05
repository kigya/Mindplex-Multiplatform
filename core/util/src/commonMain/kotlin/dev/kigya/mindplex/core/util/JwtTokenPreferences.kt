package dev.kigya.mindplex.core.util

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

suspend fun DataStore<Preferences>.getJwtToken(): String? = this.data
    .map { preferences -> preferences[stringPreferencesKey(MINDPLEX_JWT)] }
    .first()

private const val MINDPLEX_JWT = "mindplex_jwt"
