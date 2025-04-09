package dev.kigya.mindplex.feature.profile.data.repository

import dev.kigya.mindplex.core.util.dsl.runSuspendCatching
import dev.kigya.mindplex.feature.profile.data.dao.ProfileDao
import dev.kigya.mindplex.feature.profile.data.exception.UserProfileNotFoundException
import dev.kigya.mindplex.feature.profile.data.mapper.LocalProfileMapper
import dev.kigya.mindplex.feature.profile.domain.contract.ProfileDatabaseRepositoryContract
import dev.kigya.mindplex.feature.profile.domain.model.ProfileDomainModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class ProfileDatabaseRepository(
    private val profileDao: ProfileDao,
    private val dispatcher: CoroutineDispatcher,
) : ProfileDatabaseRepositoryContract {

    override suspend fun getTopUsersByToken(token: String): Result<ProfileDomainModel> =
        runSuspendCatching {
            withContext(dispatcher) {
                val userLocalProfile = profileDao.getUserByToken(token)
                if (userLocalProfile != null) {
                    LocalProfileMapper.mapToDomainModel(userLocalProfile)
                } else {
                    throw UserProfileNotFoundException("User not found")
                }
            }
        }

    override suspend fun saveUser(
        user: ProfileDomainModel,
        token: String,
    ): Result<ProfileDomainModel> = runSuspendCatching {
        withContext(dispatcher) {
            val userLocalProfile = LocalProfileMapper.mapFromDomainModel(user)
            val updatedUserLocalProfile = userLocalProfile.copy(id = token)
            profileDao.upsertUser(updatedUserLocalProfile)
            user
        }
    }
}
