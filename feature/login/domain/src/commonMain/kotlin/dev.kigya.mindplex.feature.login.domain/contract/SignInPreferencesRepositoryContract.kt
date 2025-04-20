package dev.kigya.mindplex.feature.login.domain.contract

import kotlinx.coroutines.flow.Flow

interface SignInPreferencesRepositoryContract {
    val userId: Flow<String?>
    val isSignedIn: Flow<Boolean>

    suspend fun signIn(googleIdToken: String)
    suspend fun signOut()
}
