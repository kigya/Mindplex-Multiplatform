package dev.kigya.mindplex.feature.leaderboard.data.repository

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.Direction
import dev.gitlive.firebase.firestore.Source
import dev.gitlive.firebase.firestore.firestore
import dev.kigya.mindplex.core.util.dsl.runSuspendCatching
import dev.kigya.mindplex.feature.leaderboard.data.exception.UserRankNotFoundException
import dev.kigya.mindplex.feature.leaderboard.data.mapper.toDomain
import dev.kigya.mindplex.feature.leaderboard.data.model.UserRemoteRankDto
import dev.kigya.mindplex.feature.leaderboard.domain.contract.UserRankNetworkRepositoryContract
import dev.kigya.mindplex.feature.leaderboard.domain.model.UserRankDomainModel
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
                val documentSnapshot = Firebase.firestore
                    .collection(UsersCollection.NAME)
                    .orderBy(FIELD_SCORE, Direction.DESCENDING)
                    .limit(userLimit)
                    .get(Source.DEFAULT)

                val userList = documentSnapshot.documents.map { document ->
                    document.data<UserRemoteRankDto>().toDomain()
                }

                userList
            }.onFailure { e ->
                throw UserRankNotFoundException(e.message)
            }
        }
}
