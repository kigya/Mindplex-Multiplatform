package dev.kigya.mindplex.feature.login.domain.contract

import dev.kigya.mindplex.feature.login.domain.model.GoogleUserDomainModel

interface SignInNetworkRepositoryContract {
    suspend fun signIn(googleUser: GoogleUserDomainModel)

    suspend fun signOut(googleIdToken: String)
}
