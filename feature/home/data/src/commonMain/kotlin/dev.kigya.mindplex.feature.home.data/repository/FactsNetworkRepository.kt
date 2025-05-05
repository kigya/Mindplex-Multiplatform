package dev.kigya.mindplex.feature.home.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import dev.kigya.mindplex.core.data.scout.api.ScoutNetworkClientContract
import dev.kigya.mindplex.core.data.scout.api.getReified
import dev.kigya.mindplex.core.util.getJwtToken
import dev.kigya.mindplex.feature.home.data.mapper.FactsRemoteDataMapper
import dev.kigya.mindplex.feature.home.data.mapper.FactsRemoteDataMapper.mappedBy
import dev.kigya.mindplex.feature.home.data.model.FactRemoteDto
import dev.kigya.mindplex.feature.home.domain.contract.FactsNetworkRepositoryContract
import dev.kigya.mindplex.feature.home.domain.model.FactDomainModel
import dev.kigya.outcome.Outcome
import dev.kigya.outcome.outcomeSuspendCatchingOn
import io.ktor.http.HttpHeaders
import kotlinx.coroutines.CoroutineDispatcher

class FactsNetworkRepository(
    private val scoutNetworkClientContract: ScoutNetworkClientContract,
    private val dataStore: DataStore<Preferences>,
    private val dispatcher: CoroutineDispatcher,
) : FactsNetworkRepositoryContract {
    override suspend fun fetchFacts(limit: Int): Outcome<*, List<FactDomainModel>> =
        outcomeSuspendCatchingOn(dispatcher) {
            val jwtToken = dataStore.getJwtToken()

            scoutNetworkClientContract.getReified<List<FactRemoteDto>>(
                path = arrayOf("facts"),
                params = mapOf("limit" to "$limit"),
                headers = mapOf(HttpHeaders.Authorization to "Bearer $jwtToken"),
            ) mappedBy FactsRemoteDataMapper
        }
}
