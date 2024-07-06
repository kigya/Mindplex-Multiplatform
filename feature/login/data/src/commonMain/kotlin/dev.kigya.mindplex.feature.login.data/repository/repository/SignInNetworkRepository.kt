package dev.kigya.mindplex.feature.login.data.repository.repository

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.firestore
import dev.kigya.mindplex.feature.login.domain.contract.SignInNetworkRepositoryContract
import dev.kigya.mindplex.feature.login.domain.model.GoogleUserDomainModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import dev.kigya.mindplex.core.data.firebase.FirestoreConfig.Collection.Users as UsersCollection

class SignInNetworkRepository(
    private val dispatcher: CoroutineDispatcher,
) : SignInNetworkRepositoryContract {
    override suspend fun signIn(googleUser: GoogleUserDomainModel) =
        requireNotNull(googleUser.tokenId).let { tokenId ->
            withContext(dispatcher) {
                Firebase.firestore
                    .collection(UsersCollection.NAME)
                    .document(tokenId)
                    .set(
                        hashMapOf(
                            UsersCollection.Document.NAME to googleUser.displayName,
                            UsersCollection.Document.AVATAR_URL to googleUser.profilePictureUrl.orEmpty(),
                        ),
                    )
            }
        }

    override suspend fun signOut(googleIdToken: String) = withContext(dispatcher) {
        Firebase.firestore
            .collection(UsersCollection.NAME)
            .document(googleIdToken)
            .delete()
    }
}
