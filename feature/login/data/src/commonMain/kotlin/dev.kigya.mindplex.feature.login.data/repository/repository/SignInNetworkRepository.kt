package dev.kigya.mindplex.feature.login.data.repository.repository

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.Source
import dev.gitlive.firebase.firestore.firestore
import dev.kigya.mindplex.feature.login.domain.contract.SignInNetworkRepositoryContract
import dev.kigya.mindplex.feature.login.domain.model.GoogleUserSignInDomainModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import dev.kigya.mindplex.core.data.firebase.FirestoreConfig.Collection.Users as UsersCollection

class SignInNetworkRepository(
    private val dispatcher: CoroutineDispatcher,
) : SignInNetworkRepositoryContract {
    override suspend fun signIn(googleUser: GoogleUserSignInDomainModel) =
        requireNotNull(googleUser.userId).let { userId ->
            withContext(dispatcher) {
                val documentRef = Firebase.firestore
                    .collection(UsersCollection.NAME)
                    .document(userId)

                val isExist = documentRef.get(Source.SERVER).exists

                if (isExist) {
                    documentRef.update(
                        hashMapOf(
                            UsersCollection.Document.NAME to googleUser.displayName,
                            UsersCollection.Document.AVATAR_URL to googleUser.profilePictureUrl.orEmpty(),
                            UsersCollection.Document.COUNTRY_CODE to googleUser.countryCode,
                        ),
                    )
                } else {
                    documentRef.set(
                        hashMapOf(
                            UsersCollection.Document.NAME to googleUser.displayName,
                            UsersCollection.Document.AVATAR_URL to googleUser.profilePictureUrl.orEmpty(),
                            UsersCollection.Document.COUNTRY_CODE to googleUser.countryCode,
                            UsersCollection.Document.SCORE to 0,
                        ),
                    )
                }
            }
        }

    override suspend fun signOut(googleIdToken: String) = withContext(dispatcher) {
        Firebase.firestore
            .collection(UsersCollection.NAME)
            .document(googleIdToken)
            .delete()
    }
}
