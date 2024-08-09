package dev.kigya.mindplex.feature.home.domain.contract

import dev.kigya.mindplex.feature.home.domain.model.FactDomainModel

interface FactsNetworkRepositoryContract {
    suspend fun fetchFacts(amount: Int): Result<List<FactDomainModel>>
}
