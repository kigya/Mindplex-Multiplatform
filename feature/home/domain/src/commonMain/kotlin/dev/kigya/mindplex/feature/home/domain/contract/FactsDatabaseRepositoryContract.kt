package dev.kigya.mindplex.feature.home.domain.contract

import dev.kigya.mindplex.feature.home.domain.model.FactDomainModel
import dev.kigya.outcome.Outcome

interface FactsDatabaseRepositoryContract {
    suspend fun getFacts(): Outcome<*, List<FactDomainModel>>
    suspend fun saveFacts(facts: List<FactDomainModel>)
    suspend fun deleteFacts()
}
