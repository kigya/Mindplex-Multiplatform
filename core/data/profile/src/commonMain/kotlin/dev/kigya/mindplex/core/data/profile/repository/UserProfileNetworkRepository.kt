package dev.kigya.mindplex.core.data.profile.repository

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.Source
import dev.gitlive.firebase.firestore.firestore
import dev.kigya.mindplex.core.data.profile.mapper.toDomain
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

                documentSnapshot.data<UserRemoteProfileDto>().toDomain()
            }
        }.onFailure { e ->
            println("Exception occurred: ${e.message}")
            e.printStackTrace()
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
