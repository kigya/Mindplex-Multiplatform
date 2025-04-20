package dev.kigya.mindplex.feature.leaderboard.data.repository

import dev.kigya.mindplex.feature.leaderboard.data.dao.UserRankDao
import dev.kigya.mindplex.feature.leaderboard.data.mapper.UserLocalRankMapper
import dev.kigya.mindplex.feature.leaderboard.domain.contract.UserRankDatabaseRepositoryContract
import dev.kigya.mindplex.feature.leaderboard.domain.model.UserRankDomainModel
import dev.kigya.outcome.Outcome
import dev.kigya.outcome.outcomeSuspendCatchingOn
import kotlinx.coroutines.CoroutineDispatcher

class UserRankDatabaseRepository(
    private val userRankDao: UserRankDao,
    private val dispatcher: CoroutineDispatcher,
) : UserRankDatabaseRepositoryContract {

    override suspend fun getTopUsersByScore(limit: Int): Outcome<*, List<UserRankDomainModel>> =
        outcomeSuspendCatchingOn(dispatcher) {
            userRankDao.getTopUsersByScore(limit).map(UserLocalRankMapper::mapToDomainModel)
        }

    override suspend fun saveUsers(
        users: List<UserRankDomainModel>,
    ): Outcome<*, List<UserRankDomainModel>> = outcomeSuspendCatchingOn(dispatcher) {
        val userEntities = users.map(UserLocalRankMapper::mapFromDomainModel)
        userRankDao.upsertUsers(userEntities)
        users
    }
}
