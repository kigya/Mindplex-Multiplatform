package dev.kigya.mindplex.feature.login.data.repository.repository

import dev.kigya.mindplex.core.data.scout.api.ScoutNetworkClientContract
import dev.kigya.mindplex.core.data.scout.api.postReified
import dev.kigya.mindplex.core.data.scout.impl.ScoutHeaders
import dev.kigya.mindplex.feature.login.data.model.SignInRequest
import dev.kigya.mindplex.feature.login.data.model.SignInResponse
import dev.kigya.mindplex.feature.login.domain.contract.SignInNetworkRepositoryContract
import dev.kigya.mindplex.feature.login.domain.model.GoogleUserSignInDomainModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class SignInNetworkRepository(
    private val scoutNetworkClientContract: ScoutNetworkClientContract,
    private val dispatcher: CoroutineDispatcher,
) : SignInNetworkRepositoryContract {
    override suspend fun signIn(googleUser: GoogleUserSignInDomainModel): Result<String> =
        requireNotNull(googleUser.idToken).let {
            withContext(dispatcher) {
                val response: SignInResponse = scoutNetworkClientContract.postReified(
                    path = arrayOf("user"),
                    body = SignInRequest(
                        token = googleUser.idToken,
                        displayName = googleUser.displayName,
                        avatarUrl = googleUser.profilePictureUrl,
                    ),
                    headers = arrayOf(
                        ScoutHeaders.ContentType("application/json"),
                        ScoutHeaders.GoogleJwt(it),
                    ),
                )

                Result.success(response.token)
            }
        }
}
