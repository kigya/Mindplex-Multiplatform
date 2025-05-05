package dev.kigya.mindplex.feature.leaderboard.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import dev.kigya.mindplex.core.data.scout.api.ScoutNetworkClientContract
import dev.kigya.mindplex.core.data.scout.api.getReified
import dev.kigya.mindplex.core.util.getJwtToken
import dev.kigya.mindplex.feature.leaderboard.data.mapper.UserRemoteRankMapper
import dev.kigya.mindplex.feature.leaderboard.data.model.UserRemoteRankDto
import dev.kigya.mindplex.feature.leaderboard.domain.contract.UserRankNetworkRepositoryContract
import dev.kigya.mindplex.feature.leaderboard.domain.model.UserRankDomainModel
import dev.kigya.outcome.Outcome
import dev.kigya.outcome.outcomeSuspendCatchingOn
import io.ktor.http.HttpHeaders
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.map

class UserRankNetworkRepository(
    private val scoutNetworkClientContract: ScoutNetworkClientContract,
    private val dataStore: DataStore<Preferences>,
    private val dispatcher: CoroutineDispatcher,
) : UserRankNetworkRepositoryContract {

    override suspend fun getTopUsersByScore(
        userLimit: Int,
    ): Outcome<*, List<UserRankDomainModel>> = outcomeSuspendCatchingOn(dispatcher) {
        val jwtToken = dataStore.getJwtToken()

        val response: List<UserRemoteRankDto> = scoutNetworkClientContract.getReified(
            path = arrayOf("leaderboard"),
            params = mapOf("limit" to userLimit.toString()),
            headers = mapOf(
                HttpHeaders.Authorization to "Bearer $jwtToken",
                HttpHeaders.Accept to "application/json",
            ),
        )

        response.map(UserRemoteRankMapper::mapToDomainModel)
    }
}
