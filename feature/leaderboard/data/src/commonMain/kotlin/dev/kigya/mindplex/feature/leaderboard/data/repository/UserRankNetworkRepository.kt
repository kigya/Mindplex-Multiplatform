package dev.kigya.mindplex.feature.leaderboard.data.repository

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.Direction
import dev.gitlive.firebase.firestore.firestore
import dev.kigya.mindplex.feature.leaderboard.data.exception.UserRankNotFoundException
import dev.kigya.mindplex.feature.leaderboard.data.mapper.UserRemoteRankMapper
import dev.kigya.mindplex.feature.leaderboard.data.model.UserRemoteRankDto
import dev.kigya.mindplex.feature.leaderboard.domain.contract.UserRankNetworkRepositoryContract
import dev.kigya.mindplex.feature.leaderboard.domain.model.UserRankDomainModel
import dev.kigya.outcome.Outcome
import dev.kigya.outcome.outcomeSuspendCatchingOn
import kotlinx.coroutines.CoroutineDispatcher
import dev.kigya.mindplex.core.data.firebase.FirestoreConfig.Collection.Users as UsersCollection

class UserRankNetworkRepository(
    private val dispatcher: CoroutineDispatcher,
) : UserRankNetworkRepositoryContract {

    override suspend fun getTopUsersByScore(userLimit: Int): Outcome<*, List<UserRankDomainModel>> =
        outcomeSuspendCatchingOn(dispatcher) {
            try {
                val documentSnapshot = Firebase.firestore
                    .collection(UsersCollection.NAME)
                    .orderBy(UsersCollection.Document.SCORE, Direction.DESCENDING)
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
