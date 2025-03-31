package dev.kigya.mindplex.feature.leaderboard.data.repository

import dev.kigya.mindplex.core.util.dsl.runSuspendCatching
import dev.kigya.mindplex.core.util.extension.empty
import dev.kigya.mindplex.feature.leaderboard.data.dao.UserRankDao
import dev.kigya.mindplex.feature.leaderboard.data.exception.UserRankNotFoundException
import dev.kigya.mindplex.feature.leaderboard.data.model.UserLocalData
import dev.kigya.mindplex.feature.leaderboard.data.model.UserLocalRank
import dev.kigya.mindplex.feature.leaderboard.domain.contract.UserRankDatabaseRepositoryContract
import dev.kigya.mindplex.feature.leaderboard.domain.model.UserRankDomainModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class UserRankDatabaseRepository(
    private val dispatcher: CoroutineDispatcher,
    private val userPlaceDao: UserRankDao,
) : UserRankDatabaseRepositoryContract {

    override suspend fun getTopUsersByScore(): Result<List<UserRankDomainModel>> =
        runSuspendCatching {
            withContext(dispatcher) {
                userPlaceDao.getTopUsersByScore()
                    .map { userLocalPlace ->
                        val userLocalData = userLocalPlace.userLocalData
                        UserRankDomainModel(
                            displayName = userLocalData?.name ?: String.empty,
                            profilePictureUrl = userLocalData?.avatar,
                            userCountry = userLocalData?.countryCode,
                            score = userLocalData?.score ?: 0,
                        )
                    }
            }
        }.fold(
            onSuccess = { Result.success(it) },
            onFailure = { throw UserRankNotFoundException(it.message) },
        )

    override suspend fun saveUsers(
        users: List<UserRankDomainModel>,
    ): Result<List<UserRankDomainModel>> = runSuspendCatching {
        withContext(dispatcher) {
            val userEntities = users.map { user ->
                UserLocalRank(
                    id = user.displayName.ifEmpty {
                        user.profilePictureUrl ?: String.empty
                    },
                    userLocalData = UserLocalData(
                        name = user.displayName,
                        avatar = user.profilePictureUrl ?: String.empty,
                        countryCode = user.userCountry ?: String.empty,
                        score = user.score,
                    ),
                )
            }
            userPlaceDao.upsertUsers(userEntities)
            users
        }
    }.fold(
        onSuccess = { Result.success(it) },
        onFailure = { Result.failure(it) },
    )
}
