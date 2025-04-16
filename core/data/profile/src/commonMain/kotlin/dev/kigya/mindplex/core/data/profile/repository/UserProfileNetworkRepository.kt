package dev.kigya.mindplex.core.data.profile.repository

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.Direction
import dev.gitlive.firebase.firestore.Source
import dev.gitlive.firebase.firestore.firestore
import dev.kigya.mindplex.core.data.profile.mapper.UserRemoteProfileMapper
import dev.kigya.mindplex.core.data.profile.model.UserRemoteProfileDto
import dev.kigya.mindplex.core.domain.profile.contract.UserProfileNetworkRepositoryContract
import dev.kigya.mindplex.core.domain.profile.model.UserProfileDomainModel
import dev.kigya.mindplex.core.util.dsl.runSuspendCatching
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import dev.kigya.mindplex.core.data.firebase.FirestoreConfig.Collection.Users as UsersCollection

class UserProfileNetworkRepository(
    private val dispatcher: CoroutineDispatcher,
) : UserProfileNetworkRepositoryContract {

    override suspend fun getUserProfile(token: String): Result<UserProfileDomainModel> =
        runSuspendCatching {
            withContext(dispatcher) {
                val documentSnapshot = Firebase.firestore
                    .collection(UsersCollection.NAME)
                    .document(token)
                    .get(Source.SERVER)

                val userDto = documentSnapshot.data<UserRemoteProfileDto>()
                val domainProfile = UserRemoteProfileMapper.mapToDomainModel(userDto)

                val globalRank = getGlobalRank(userDto.score)
                val localRank = userDto.countryCode?.let {
                    getLocalRank(userDto.score, it)
                }

                val finalProfile = domainProfile.copy(
                    globalRank = globalRank,
                    localRank = localRank,
                )
                return@withContext finalProfile
            }
        }

    private suspend fun getGlobalRank(userScore: Int): Int {
        val allUsersSnapshot = Firebase.firestore
            .collection(UsersCollection.NAME)
            .orderBy(UsersCollection.Document.SCORE, Direction.DESCENDING)
            .get()

        return allUsersSnapshot.documents.indexOfFirst {
            (it.get(UsersCollection.Document.SCORE) ?: 0L) <= userScore.toLong()
        } + 1
    }

    private suspend fun getLocalRank(
        userScore: Int,
        countryCode: String,
    ): Int {
        val localUsersSnapshot = Firebase.firestore
            .collection(UsersCollection.NAME)
            .orderBy(UsersCollection.Document.SCORE, Direction.DESCENDING)
            .get()

        val filteredUsersSnapshot = localUsersSnapshot.documents.filter {
            it.get<String>(UsersCollection.Document.COUNTRY_CODE) == countryCode
        }

        return filteredUsersSnapshot.indexOfFirst {
            (it.get(UsersCollection.Document.SCORE) ?: 0L) <= userScore.toLong()
        } + 1
    }

    override suspend fun updateUserScore(
        token: String,
        score: Int,
    ) {
        withContext(dispatcher) {
            Firebase.firestore
                .collection(UsersCollection.NAME)
                .document(token)
                .update(hashMapOf(UsersCollection.Document.SCORE to score))
        }
    }
}
