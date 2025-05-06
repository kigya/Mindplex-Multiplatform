package dev.kigya.mindplex.feature.leaderboard.data.repository

import dev.kigya.mindplex.core.data.scout.api.ScoutNetworkClientContract
import dev.kigya.mindplex.core.data.scout.api.getReified
import dev.kigya.mindplex.core.data.scout.impl.ScoutHeaders
import dev.kigya.mindplex.feature.leaderboard.data.mapper.UserRemoteRankMapper
import dev.kigya.mindplex.feature.leaderboard.data.model.UserRemoteRankDto
import dev.kigya.mindplex.feature.leaderboard.domain.contract.UserRankNetworkRepositoryContract
import dev.kigya.mindplex.feature.leaderboard.domain.model.UserRankDomainModel
import dev.kigya.outcome.Outcome
import dev.kigya.outcome.outcomeSuspendCatchingOn
import kotlinx.coroutines.CoroutineDispatcher

class UserRankNetworkRepository(
    private val scoutNetworkClientContract: ScoutNetworkClientContract,
    private val dispatcher: CoroutineDispatcher,
) : UserRankNetworkRepositoryContract {

    override suspend fun getTopUsersByScore(
        userLimit: Int,
    ): Outcome<*, List<UserRankDomainModel>> = outcomeSuspendCatchingOn(dispatcher) {
        val response: List<UserRemoteRankDto> = scoutNetworkClientContract.getReified(
            path = arrayOf("leaderboard"),
            params = mapOf("limit" to userLimit.toString()),
            headers = arrayOf(ScoutHeaders.MindplexJwt),
        )

        response.map(UserRemoteRankMapper::mapToDomainModel)
    }
}
