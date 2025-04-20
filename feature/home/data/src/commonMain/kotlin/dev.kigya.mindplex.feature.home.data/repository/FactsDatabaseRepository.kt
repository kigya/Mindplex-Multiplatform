package dev.kigya.mindplex.feature.home.data.repository

import dev.kigya.mindplex.feature.home.data.dao.FactsDao
import dev.kigya.mindplex.feature.home.data.mapper.FactsLocalDataMapper.mapFromDomainModel
import dev.kigya.mindplex.feature.home.data.mapper.FactsLocalDataMapper.mapToDomainModel
import dev.kigya.mindplex.feature.home.domain.contract.FactsDatabaseRepositoryContract
import dev.kigya.mindplex.feature.home.domain.model.FactDomainModel
import dev.kigya.outcome.Outcome
import dev.kigya.outcome.outcomeSuspendCatchingOn
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class FactsDatabaseRepository(
    private val factsDao: FactsDao,
    private val dispatcher: CoroutineDispatcher,
) : FactsDatabaseRepositoryContract {
    override suspend fun getFacts(): Outcome<*, List<FactDomainModel>> =
        outcomeSuspendCatchingOn(dispatcher) {
            val facts = factsDao.get()
            require(facts.isNotEmpty())
            mapToDomainModel(facts)
        }

    override suspend fun saveFacts(facts: List<FactDomainModel>) = withContext(dispatcher) {
        factsDao.upsert(mapFromDomainModel(facts))
    }

    override suspend fun deleteFacts() = withContext(dispatcher) {
        factsDao.deleteAll()
    }
}
