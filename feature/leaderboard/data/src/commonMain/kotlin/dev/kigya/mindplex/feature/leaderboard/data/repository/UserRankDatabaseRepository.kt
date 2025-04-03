package dev.kigya.mindplex.feature.leaderboard.data.repository

import dev.kigya.mindplex.core.util.dsl.runSuspendCatching
import dev.kigya.mindplex.feature.leaderboard.data.dao.UserRankDao
import dev.kigya.mindplex.feature.leaderboard.data.mapper.UserLocalRankMapper
import dev.kigya.mindplex.feature.leaderboard.domain.contract.UserRankDatabaseRepositoryContract
import dev.kigya.mindplex.feature.leaderboard.domain.model.UserRankDomainModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class UserRankDatabaseRepository(
    private val userPlaceDao: UserRankDao,
    private val dispatcher: CoroutineDispatcher,
) : UserRankDatabaseRepositoryContract {

    override suspend fun getTopUsersByScore(limit: Int): Result<List<UserRankDomainModel>> =
        runSuspendCatching {
            withContext(dispatcher) {
                userPlaceDao.getTopUsersByScore(limit).map(UserLocalRankMapper::mapToDomainModel)
            }
        }

    override suspend fun saveUsers(
        users: List<UserRankDomainModel>,
    ): Result<List<UserRankDomainModel>> = runSuspendCatching {
        withContext(dispatcher) {
            val userEntities = users.map(UserLocalRankMapper::mapFromDomainModel)
            userPlaceDao.upsertUsers(userEntities)
            users
        }
    }
}
