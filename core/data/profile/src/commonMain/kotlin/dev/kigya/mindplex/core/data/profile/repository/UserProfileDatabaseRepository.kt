package dev.kigya.mindplex.core.data.profile.repository

import dev.kigya.mindplex.core.data.profile.dao.UserProfileDao
import dev.kigya.mindplex.core.data.profile.exception.ScoreRetrievalException
import dev.kigya.mindplex.core.data.profile.exception.UserProfileNotFoundException
import dev.kigya.mindplex.core.data.profile.mapper.UserLocalProfileMapper
import dev.kigya.mindplex.core.domain.profile.contract.UserProfileDatabaseRepositoryContract
import dev.kigya.mindplex.core.domain.profile.model.UserProfileDomainModel
import dev.kigya.mindplex.core.util.dsl.runSuspendCatching
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class UserProfileDatabaseRepository(
    private val dispatcher: CoroutineDispatcher,
    private val userProfileDao: UserProfileDao,
) : UserProfileDatabaseRepositoryContract {

    override suspend fun getUserProfile(token: String): Result<UserProfileDomainModel> =
        runSuspendCatching {
            withContext(dispatcher) {
                val localProfile = userProfileDao.getProfile(token)
                    ?: throw UserProfileNotFoundException("User not found")
                UserLocalProfileMapper.mapToDomainModel(localProfile)
            }
        }

    override suspend fun getUserScore(token: String): Result<Int> = runSuspendCatching {
        withContext(dispatcher) {
            userProfileDao.getScore(token)
        } ?: throw ScoreRetrievalException("Cannot get score")
    }

    override suspend fun saveUserScore(
        token: String,
        score: Int,
    ) {
        runSuspendCatching {
            withContext(dispatcher) {
                userProfileDao.updateScore(token, score)
            }
        }
    }

    override suspend fun saveUserProfile(
        token: String,
        profile: UserProfileDomainModel,
    ) {
        runSuspendCatching {
            withContext(dispatcher) {
                val localProfile = UserLocalProfileMapper.mapFromDomainModel(profile).copy(id = token)
                userProfileDao.upsertProfile(localProfile)
            }
        }
    }
}
