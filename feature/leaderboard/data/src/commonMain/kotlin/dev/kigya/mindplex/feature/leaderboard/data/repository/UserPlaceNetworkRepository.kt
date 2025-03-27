package dev.kigya.mindplex.feature.leaderboard.data.repository

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.Direction
import dev.gitlive.firebase.firestore.Source
import dev.gitlive.firebase.firestore.firestore
import dev.kigya.mindplex.core.util.dsl.runSuspendCatching
import dev.kigya.mindplex.feature.leaderboard.data.exception.UserPlaceNotFoundException
import dev.kigya.mindplex.feature.leaderboard.data.mapper.toDomain
import dev.kigya.mindplex.feature.leaderboard.data.model.UserRemotePlaceDto
import dev.kigya.mindplex.feature.leaderboard.domain.contract.UserPlaceNetworkRepositoryContract
import dev.kigya.mindplex.feature.leaderboard.domain.model.UserPlaceDomainModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import dev.kigya.mindplex.core.data.firebase.FirestoreConfig.Collection.Users as UsersCollection

private const val FIELD_SCORE = "score"

class UserPlaceNetworkRepository(
    private val dispatcher: CoroutineDispatcher,
) : UserPlaceNetworkRepositoryContract {

    override suspend fun getTopUsersByScore(userLimit: Int): Result<List<UserPlaceDomainModel>> =
        withContext(dispatcher) {
            runSuspendCatching {
                val documentSnapshot = Firebase.firestore
                    .collection(UsersCollection.NAME)
                    .orderBy(FIELD_SCORE, Direction.DESCENDING)
                    .limit(userLimit)
                    .get(Source.SERVER)

                val userList = documentSnapshot.documents.map { document ->
                    document.data<UserRemotePlaceDto>().toDomain()
                }

                userList
            }.onFailure { e ->
                e.message?.let { UserPlaceNotFoundException(it) }
                e.printStackTrace()
            }
        }
}
