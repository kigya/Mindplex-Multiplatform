package dev.kigya.mindplex.feature.leaderboard.data.repository

import dev.kigya.mindplex.core.util.dsl.runSuspendCatching
import dev.kigya.mindplex.core.util.extension.empty
import dev.kigya.mindplex.feature.leaderboard.data.dao.UserPlaceDao
import dev.kigya.mindplex.feature.leaderboard.data.exception.UserPlaceNotFoundException
import dev.kigya.mindplex.feature.leaderboard.domain.contract.UserPlaceDatabaseRepositoryContract
import dev.kigya.mindplex.feature.leaderboard.domain.model.UserPlaceDomainModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class UserPlaceDatabaseRepository(
    private val dispatcher: CoroutineDispatcher,
    private val userPlaceDao: UserPlaceDao,
) : UserPlaceDatabaseRepositoryContract {

    override suspend fun getTopUsersByScore(): Result<List<UserPlaceDomainModel>> =
        runSuspendCatching {
            withContext(dispatcher) {
                userPlaceDao.getTopUsersByScore()
                    .map { userLocalPlace ->
                        val userLocalData = userLocalPlace.userLocalData
                        UserPlaceDomainModel(
                            displayName = userLocalData?.name ?: String.empty,
                            profilePictureUrl = userLocalData?.avatar,
                            score = userLocalData?.score ?: 0,
                        )
                    }
            }
        }.fold(
            onSuccess = { Result.success(it) },
            onFailure = { throw UserPlaceNotFoundException(it.message) },
        )
}
