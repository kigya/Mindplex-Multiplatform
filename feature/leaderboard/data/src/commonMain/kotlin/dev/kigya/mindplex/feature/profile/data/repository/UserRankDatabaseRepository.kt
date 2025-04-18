package dev.kigya.mindplex.feature.profile.data.repository

import dev.kigya.mindplex.core.util.dsl.runSuspendCatching
import dev.kigya.mindplex.feature.profile.data.dao.UserRankDao
import dev.kigya.mindplex.feature.profile.data.mapper.UserLocalRankMapper
import dev.kigya.mindplex.feature.profile.domain.contract.UserRankDatabaseRepositoryContract
import dev.kigya.mindplex.feature.profile.domain.model.UserRankDomainModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class UserRankDatabaseRepository(
    private val userRankDao: UserRankDao,
    private val dispatcher: CoroutineDispatcher,
) : UserRankDatabaseRepositoryContract {

    override suspend fun getTopUsersByScore(limit: Int): Result<List<UserRankDomainModel>> =
        runSuspendCatching {
            withContext(dispatcher) {
                userRankDao.getTopUsersByScore(limit).map(UserLocalRankMapper::mapToDomainModel)
            }
        }

    override suspend fun saveUsers(
        users: List<UserRankDomainModel>,
    ): Result<List<UserRankDomainModel>> = runSuspendCatching {
        withContext(dispatcher) {
            val userEntities = users.map(UserLocalRankMapper::mapFromDomainModel)
            userRankDao.upsertUsers(userEntities)
            users
        }
    }
}
