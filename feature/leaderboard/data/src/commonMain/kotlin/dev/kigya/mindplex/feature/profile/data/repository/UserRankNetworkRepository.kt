package dev.kigya.mindplex.feature.profile.data.repository

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.Direction
import dev.gitlive.firebase.firestore.firestore
import dev.kigya.mindplex.core.util.dsl.runSuspendCatching
import dev.kigya.mindplex.feature.profile.data.exception.UserRankNotFoundException
import dev.kigya.mindplex.feature.profile.data.mapper.UserRemoteRankMapper
import dev.kigya.mindplex.feature.profile.data.model.UserRemoteRankDto
import dev.kigya.mindplex.feature.profile.domain.contract.UserRankNetworkRepositoryContract
import dev.kigya.mindplex.feature.profile.domain.model.UserRankDomainModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import dev.kigya.mindplex.core.data.firebase.FirestoreConfig.Collection.Users as UsersCollection

private const val FIELD_SCORE = "score"

class UserRankNetworkRepository(
    private val dispatcher: CoroutineDispatcher,
) : UserRankNetworkRepositoryContract {

    override suspend fun getTopUsersByScore(userLimit: Int): Result<List<UserRankDomainModel>> =
        withContext(dispatcher) {
            runSuspendCatching {
                try {
                    val documentSnapshot = Firebase.firestore
                        .collection(UsersCollection.NAME)
                        .orderBy(FIELD_SCORE, Direction.DESCENDING)
                        .limit(userLimit)
                        .get()

                    documentSnapshot.documents.map { document ->
                        val userDto = document.data<UserRemoteRankDto>()
                        UserRemoteRankMapper.mapToDomainModel(userDto)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    throw UserRankNotFoundException("Failed to fetch top users by score: ${e.message}")
                }
            }
        }
}
