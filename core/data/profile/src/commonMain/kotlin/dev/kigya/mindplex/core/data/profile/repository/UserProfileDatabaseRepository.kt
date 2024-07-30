package dev.kigya.mindplex.core.data.profile.repository

import dev.kigya.mindplex.core.data.profile.dao.UserProfileDao
import dev.kigya.mindplex.core.data.profile.exceptions.UserProfileNotFoundException
import dev.kigya.mindplex.core.data.profile.mapper.toDatabaseEntry
import dev.kigya.mindplex.core.data.profile.mapper.toDomain
import dev.kigya.mindplex.core.domain.profile.contract.UserProfileDatabaseRepositoryContract
import dev.kigya.mindplex.core.domain.profile.model.UserProfileDomainModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class UserProfileDatabaseRepository(
    private val dispatcher: CoroutineDispatcher,
    private val userProfileDao: UserProfileDao,
) : UserProfileDatabaseRepositoryContract {

    override suspend fun getUserProfile(token: String): Result<UserProfileDomainModel> =
        runCatching {
            withContext(dispatcher) {
                userProfileDao.get(token)?.toDomain()
            } ?: throw UserProfileNotFoundException("User not found")
        }

    override suspend fun saveUserProfile(token: String, profile: UserProfileDomainModel) {
        runCatching {
            withContext(dispatcher) {
                userProfileDao.upsert(profile.toDatabaseEntry(token))
            }
        }
    }
}
