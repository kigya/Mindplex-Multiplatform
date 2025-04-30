package dev.kigya.mindplex.feature.home.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import dev.kigya.mindplex.core.data.scout.api.ScoutNetworkClientContract
import dev.kigya.mindplex.core.data.scout.api.getReified
import dev.kigya.mindplex.feature.home.data.mapper.FactsRemoteDataMapper
import dev.kigya.mindplex.feature.home.data.mapper.FactsRemoteDataMapper.mappedBy
import dev.kigya.mindplex.feature.home.data.model.FactRemoteDto
import dev.kigya.mindplex.feature.home.domain.contract.FactsNetworkRepositoryContract
import dev.kigya.mindplex.feature.home.domain.model.FactDomainModel
import dev.kigya.outcome.Outcome
import dev.kigya.outcome.outcomeSuspendCatchingOn
import io.ktor.http.HttpHeaders
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class FactsNetworkRepository(
    private val scoutNetworkClientContract: ScoutNetworkClientContract,
    private val dataStore: DataStore<Preferences>,
    private val dispatcher: CoroutineDispatcher,
) : FactsNetworkRepositoryContract {
    override suspend fun fetchFacts(limit: Int): Outcome<*, List<FactDomainModel>> =
        outcomeSuspendCatchingOn(dispatcher) {
            val jwtToken = dataStore.data.map { preferences ->
                preferences[stringPreferencesKey(MINDPLEX_JWT)]
            }.first()

            scoutNetworkClientContract.getReified<List<FactRemoteDto>>(
                path = arrayOf("facts"),
                params = mapOf("limit" to "$limit"),
                headers = mapOf(HttpHeaders.Authorization to "Bearer $jwtToken"),
            ) mappedBy FactsRemoteDataMapper
        }

    private companion object {
        const val MINDPLEX_JWT = "mindplex_jwt"
    }
}
