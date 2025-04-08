package dev.kigya.mindplex.feature.profile.data.repository

import dev.kigya.mindplex.core.util.dsl.runSuspendCatching
import dev.kigya.mindplex.feature.profile.data.dao.ProfileDao
import dev.kigya.mindplex.feature.profile.data.exception.UserProfileNotFoundException
import dev.kigya.mindplex.feature.profile.data.mapper.UserLocalProfileMapper
import dev.kigya.mindplex.feature.profile.domain.contract.ProfileDatabaseRepositoryContract
import dev.kigya.mindplex.feature.profile.domain.model.UserProfileDomainModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class ProfileDatabaseRepository(
    private val profileDao: ProfileDao,
    private val dispatcher: CoroutineDispatcher,
) : ProfileDatabaseRepositoryContract {

    override suspend fun getTopUsersByToken(token: String): Result<UserProfileDomainModel> =
        runSuspendCatching {
            withContext(dispatcher) {
                val userLocalProfile = profileDao.getUserByToken(token)
                if (userLocalProfile != null) {
                    UserLocalProfileMapper.mapToDomainModel(userLocalProfile)
                } else {
                    throw UserProfileNotFoundException("User not found")
                }
            }
        }

    override suspend fun saveUser(
        user: UserProfileDomainModel,
        token: String,
    ): Result<UserProfileDomainModel> = runSuspendCatching {
        withContext(dispatcher) {
            val userLocalProfile = UserLocalProfileMapper.mapFromDomainModel(user)
            val updatedUserLocalProfile = userLocalProfile.copy(id = token)
            profileDao.upsertUser(updatedUserLocalProfile)
            user
        }
    }
}
