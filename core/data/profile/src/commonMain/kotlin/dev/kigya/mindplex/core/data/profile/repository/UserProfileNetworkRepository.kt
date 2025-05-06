package dev.kigya.mindplex.core.data.profile.repository

import dev.kigya.mindplex.core.data.profile.mapper.UserRemoteProfileMapper
import dev.kigya.mindplex.core.data.profile.model.UserRemoteProfileDto
import dev.kigya.mindplex.core.data.scout.api.ScoutNetworkClientContract
import dev.kigya.mindplex.core.data.scout.api.getReified
import dev.kigya.mindplex.core.data.scout.api.patchReified
import dev.kigya.mindplex.core.data.scout.impl.ScoutHeaders
import dev.kigya.mindplex.core.domain.profile.contract.UserProfileNetworkRepositoryContract
import dev.kigya.mindplex.core.domain.profile.model.UserProfileDomainModel
import dev.kigya.outcome.Outcome
import dev.kigya.outcome.outcomeSuspendCatchingOn
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive

class UserProfileNetworkRepository(
    private val scoutNetworkClientContract: ScoutNetworkClientContract,
    private val dispatcher: CoroutineDispatcher,
) : UserProfileNetworkRepositoryContract {

    override suspend fun getUserProfile(jwtToken: String): Outcome<*, UserProfileDomainModel> =
        outcomeSuspendCatchingOn(dispatcher) {
            val userDto: UserRemoteProfileDto = scoutNetworkClientContract.getReified(
                path = arrayOf(FIRESTORE_COLLECTION_USER, FIRESTORE_DOCUMENT_PROFILE),
                headers = arrayOf(ScoutHeaders.MindplexJwt),
            )

            val domainModel = UserRemoteProfileMapper.mapToDomainModel(userDto)
            domainModel
        }

    override suspend fun updateUserScore(
        jwtToken: String,
        score: Int,
    ) {
        withContext(dispatcher) {
            scoutNetworkClientContract.patchReified<Unit>(
                path = arrayOf(FIRESTORE_COLLECTION_USER, FIRESTORE_FIELD_SCORE),
                headers = arrayOf(ScoutHeaders.MindplexJwt),
                body = JsonObject(mapOf("delta" to JsonPrimitive(score))),
            )
        }
    }

    private companion object {
        const val FIRESTORE_COLLECTION_USER = "user"
        const val FIRESTORE_FIELD_SCORE = "score"
        const val FIRESTORE_DOCUMENT_PROFILE = "profile"
    }
}
