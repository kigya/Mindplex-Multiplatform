package dev.kigya.mindplex.feature.home.domain.contract

import dev.kigya.mindplex.feature.home.domain.model.FactDomainModel
import dev.kigya.outcome.Outcome

interface FactsNetworkRepositoryContract {
    suspend fun fetchFacts(amount: Int): Outcome<*, List<FactDomainModel>>
}
