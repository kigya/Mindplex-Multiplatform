package dev.kigya.mindplex.core.data.credentials.internal

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.Source
import dev.gitlive.firebase.firestore.firestore
import dev.kigya.mindplex.core.data.credentials.api.SecretsProviderContract
import dev.kigya.mindplex.core.data.credentials.model.ApiKeyRemoteDto
import dev.kigya.mindplex.core.util.dsl.runSuspendCatching
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import dev.kigya.mindplex.core.data.firebase.FirestoreConfig.Collection.Secrets as SecretsCollection

class SecretsProvider(private val dispatcher: CoroutineDispatcher) : SecretsProviderContract {
    override suspend fun provideFlagsKey(): Result<String> = withContext(dispatcher) {
        runSuspendCatching {
            val documentSnapshot = Firebase.firestore
                .collection(SecretsCollection.NAME)
                .document(SecretsCollection.Document.FLAGS_API)
                .get(Source.SERVER)

            documentSnapshot.data<ApiKeyRemoteDto>().flagsKey
        }
    }
}
