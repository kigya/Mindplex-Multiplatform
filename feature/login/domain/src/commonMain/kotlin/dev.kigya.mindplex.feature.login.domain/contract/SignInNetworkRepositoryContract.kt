package dev.kigya.mindplex.feature.login.domain.contract

import dev.kigya.mindplex.feature.login.domain.model.GoogleUserSignInDomainModel

interface SignInNetworkRepositoryContract {
    suspend fun signIn(googleUser: GoogleUserSignInDomainModel)

    suspend fun signOut(googleIdToken: String)
}
