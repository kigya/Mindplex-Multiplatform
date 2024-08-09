package dev.kigya.mindplex.feature.home.domain.contract

import dev.kigya.mindplex.feature.home.domain.model.FactDomainModel

interface FactsDatabaseRepositoryContract {
    suspend fun getFacts(): Result<List<FactDomainModel>>
    suspend fun saveFacts(facts: List<FactDomainModel>)
    suspend fun deleteFacts()
}
