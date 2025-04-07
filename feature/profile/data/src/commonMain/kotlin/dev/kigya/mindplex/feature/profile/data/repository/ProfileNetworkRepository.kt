package dev.kigya.mindplex.feature.profile.data.repository

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.Direction
import dev.gitlive.firebase.firestore.Source
import dev.gitlive.firebase.firestore.firestore
import dev.kigya.mindplex.core.util.dsl.runSuspendCatching
import dev.kigya.mindplex.feature.profile.data.mapper.UserRemoteProfileMapper
import dev.kigya.mindplex.feature.profile.data.model.UserRemoteProfileDto
import dev.kigya.mindplex.feature.profile.domain.contract.ProfileNetworkRepositoryContract
import dev.kigya.mindplex.feature.profile.domain.model.UserProfileDomainModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import dev.kigya.mindplex.core.data.firebase.FirestoreConfig.Collection.Users as UsersCollection

private const val FIELD_SCORE = "score"
private const val FIELD_COUNTRY_CODE = "countryCode"

class ProfileNetworkRepository(
    private val dispatcher: CoroutineDispatcher,
) : ProfileNetworkRepositoryContract {

    override suspend fun getTopUsersByToken(token: String): Result<UserProfileDomainModel> =
        withContext(dispatcher) {
            runSuspendCatching {
                val documentSnapshot = Firebase.firestore
                    .collection(UsersCollection.NAME)
                    .document(token)
                    .get(Source.SERVER)

                val userDto = documentSnapshot.data<UserRemoteProfileDto>()
                val domainProfile = UserRemoteProfileMapper.mapToDomainModel(userDto)

                val globalRank = getGlobalRank(userDto.score)
                val localRank = getLocalRank(userDto.score, userDto.countryCode)

                val finalProfile = domainProfile.copy(
                    globalRank = globalRank,
                    localRank = localRank,
                )
                return@runSuspendCatching finalProfile
            }
        }

    private suspend fun getGlobalRank(userScore: Int): Int {
        val allUsersSnapshot = Firebase.firestore
            .collection(UsersCollection.NAME)
            .orderBy(FIELD_SCORE, Direction.DESCENDING)
            .get()

        return allUsersSnapshot.documents.indexOfFirst {
            (it.get(FIELD_SCORE) ?: 0L) <= userScore.toLong()
        } + 1
    }

    private suspend fun getLocalRank(
        userScore: Int,
        countryCode: String,
    ): Int {
        val localUsersSnapshot = Firebase.firestore
            .collection(UsersCollection.NAME)
            .orderBy(FIELD_SCORE, Direction.DESCENDING)
            .get()

        val filteredUsersSnapshot = localUsersSnapshot.documents.filter {
            it.get<String>(FIELD_COUNTRY_CODE) == countryCode
        }

        return filteredUsersSnapshot.indexOfFirst {
            (it.get(FIELD_SCORE) ?: 0L) <= userScore.toLong()
        } + 1
    }
}
