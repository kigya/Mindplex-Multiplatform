package dev.kigya.mindplex.feature.home.data.repository

import dev.kigya.mindplex.core.data.scout.api.ScoutNetworkClientContract
import dev.kigya.mindplex.core.data.scout.api.fetchReified
import dev.kigya.mindplex.feature.home.data.mapper.FactsRemoteDataMapper
import dev.kigya.mindplex.feature.home.data.mapper.FactsRemoteDataMapper.mappedBy
import dev.kigya.mindplex.feature.home.data.model.FactRemoteDto
import dev.kigya.mindplex.feature.home.domain.contract.FactsNetworkRepositoryContract
import dev.kigya.mindplex.feature.home.domain.model.FactDomainModel
import dev.kigya.outcome.Outcome
import dev.kigya.outcome.outcomeSuspendCatchingOn
import kotlinx.coroutines.CoroutineDispatcher

class FactsNetworkRepository(
    private val scoutNetworkClientContract: ScoutNetworkClientContract,
    private val dispatcher: CoroutineDispatcher,
) : FactsNetworkRepositoryContract {
    override suspend fun fetchFacts(limit: Int): Outcome<*, List<FactDomainModel>> =
        outcomeSuspendCatchingOn(dispatcher) {
            scoutNetworkClientContract.fetchReified<List<FactRemoteDto>>(
                path = arrayOf("facts"),
                params = mapOf("limit" to "$limit"),
            ) mappedBy FactsRemoteDataMapper
        }
}
