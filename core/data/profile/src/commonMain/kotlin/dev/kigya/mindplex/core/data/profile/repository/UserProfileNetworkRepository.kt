package dev.kigya.mindplex.core.data.profile.repository

import dev.kigya.mindplex.core.data.profile.mapper.UserRemoteProfileMapper
import dev.kigya.mindplex.core.data.profile.model.ScoreDeltaDto
import dev.kigya.mindplex.core.data.profile.model.ScoreUpdateResponseDto
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

private const val MINDPLEX_BACKEND_COLLECTION_USER = "user"

class UserProfileNetworkRepository(
    private val scoutNetworkClientContract: ScoutNetworkClientContract,
    private val dispatcher: CoroutineDispatcher,
) : UserProfileNetworkRepositoryContract {

    override suspend fun getUserProfile(jwtToken: String): Outcome<*, UserProfileDomainModel> =
        outcomeSuspendCatchingOn(dispatcher) {
            val userDto: UserRemoteProfileDto = scoutNetworkClientContract.getReified(
                path = arrayOf(MINDPLEX_BACKEND_COLLECTION_USER, "profile"),
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
            scoutNetworkClientContract.patchReified<ScoreUpdateResponseDto>(
                path = arrayOf(MINDPLEX_BACKEND_COLLECTION_USER, "score"),
                headers = arrayOf(ScoutHeaders.MindplexJwt),
                body = ScoreDeltaDto(score),
            )
        }
    }
}
