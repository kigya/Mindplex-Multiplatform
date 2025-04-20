package dev.kigya.mindplex.core.data.profile.repository

import dev.kigya.mindplex.core.data.profile.dao.UserProfileDao
import dev.kigya.mindplex.core.data.profile.exception.ScoreRetrievalException
import dev.kigya.mindplex.core.data.profile.exception.UserProfileNotFoundException
import dev.kigya.mindplex.core.data.profile.mapper.UserLocalProfileMapper
import dev.kigya.mindplex.core.domain.profile.contract.UserProfileDatabaseRepositoryContract
import dev.kigya.mindplex.core.domain.profile.model.UserProfileDomainModel
import dev.kigya.outcome.Outcome
import dev.kigya.outcome.outcomeSuspendCatchingOn
import kotlinx.coroutines.CoroutineDispatcher

class UserProfileDatabaseRepository(
    private val dispatcher: CoroutineDispatcher,
    private val userProfileDao: UserProfileDao,
) : UserProfileDatabaseRepositoryContract {

    override suspend fun getUserProfile(userId: String): Outcome<*, UserProfileDomainModel> =
        outcomeSuspendCatchingOn(dispatcher) {
            val localProfile = userProfileDao.getProfile(userId)
                ?: throw UserProfileNotFoundException("User not found")
            UserLocalProfileMapper.mapToDomainModel(localProfile)
        }

    override suspend fun getUserScore(userId: String): Outcome<*, Int> =
        outcomeSuspendCatchingOn(dispatcher) {
            userProfileDao.getScore(userId) ?: throw ScoreRetrievalException("Cannot get score")
        }

    override suspend fun saveUserScore(
        userId: String,
        score: Int,
    ) {
        outcomeSuspendCatchingOn(dispatcher) {
            userProfileDao.updateScore(userId, score)
        }
    }

    override suspend fun saveUserProfile(
        userId: String,
        profile: UserProfileDomainModel,
    ) {
        outcomeSuspendCatchingOn(dispatcher) {
            val localProfile =
                UserLocalProfileMapper.mapFromDomainModel(profile).copy(id = userId)
            userProfileDao.upsertProfile(localProfile)
        }
    }
}
