package dev.kigya.mindpex.feature.home.data.repository

import dev.kigya.mindpex.feature.home.data.dao.FactsDao
import dev.kigya.mindpex.feature.home.data.mapper.toDomain
import dev.kigya.mindpex.feature.home.data.mapper.toLocalEntity
import dev.kigya.mindplex.core.util.dsl.runSuspendCatching
import dev.kigya.mindplex.feature.home.domain.contract.FactsDatabaseRepositoryContract
import dev.kigya.mindplex.feature.home.domain.model.FactDomainModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class FactsDatabaseRepository(
    private val factsDao: FactsDao,
    private val dispatcher: CoroutineDispatcher,
) : FactsDatabaseRepositoryContract {
    override suspend fun getFacts(): Result<List<FactDomainModel>> = withContext(dispatcher) {
        runSuspendCatching {
            val facts = factsDao.get()
            require(facts.isNotEmpty())
            facts.toDomain()
        }
    }

    override suspend fun saveFacts(facts: List<FactDomainModel>) = withContext(dispatcher) {
        factsDao.upsert(facts.toLocalEntity())
    }

    override suspend fun deleteFacts() = withContext(dispatcher) {
        factsDao.deleteAll()
    }
}
