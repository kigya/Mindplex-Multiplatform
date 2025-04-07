package dev.kigya.mindplex.feature.home.data.repository

import dev.kigya.mindplex.core.data.scout.api.ScoutNetworkClientContract
import dev.kigya.mindplex.core.data.scout.api.fetchReified
import dev.kigya.mindplex.core.util.dsl.runSuspendCatching
import dev.kigya.mindplex.feature.home.data.mapper.FactsRemoteDataMapper
import dev.kigya.mindplex.feature.home.data.mapper.FactsRemoteDataMapper.mappedBy
import dev.kigya.mindplex.feature.home.data.model.FactRemoteDto
import dev.kigya.mindplex.feature.home.domain.contract.FactsNetworkRepositoryContract
import dev.kigya.mindplex.feature.home.domain.model.FactDomainModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class FactsNetworkRepository(
    private val scoutNetworkClientContract: ScoutNetworkClientContract,
    private val dispatcher: CoroutineDispatcher,
) : FactsNetworkRepositoryContract {
    override suspend fun fetchFacts(limit: Int): Result<List<FactDomainModel>> =
        runSuspendCatching {
            withContext(dispatcher) {
                scoutNetworkClientContract.fetchReified<List<FactRemoteDto>>(
                    path = arrayOf("facts"),
                    params = mapOf("limit" to "$limit"),
                ) mappedBy FactsRemoteDataMapper
            }
        }
}
