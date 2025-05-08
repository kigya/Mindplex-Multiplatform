package dev.kigya.mindplex.feature.login.domain.contract

import dev.kigya.mindplex.feature.login.domain.model.GoogleUserSignInDomainModel
import dev.kigya.outcome.Outcome

interface SignInNetworkRepositoryContract {
    suspend fun signIn(googleUser: GoogleUserSignInDomainModel): Outcome<*, String>
}
