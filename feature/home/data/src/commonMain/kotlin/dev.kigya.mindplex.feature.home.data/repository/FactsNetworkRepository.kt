package dev.kigya.mindplex.feature.home.data.repository

import dev.kigya.mindplex.core.data.credentials.api.SecretsProviderContract
import dev.kigya.mindplex.core.data.firebase.FirestoreConfig.Collection.Secrets.Document
import dev.kigya.mindplex.core.util.dsl.runSuspendCatching
import dev.kigya.mindplex.feature.home.data.exception.FailedToFetchFactException
import dev.kigya.mindplex.feature.home.data.exception.FailedToProvideApiKeyException
import dev.kigya.mindplex.feature.home.data.mapper.toDomain
import dev.kigya.mindplex.feature.home.data.model.FactRemoteDto
import dev.kigya.mindplex.feature.home.domain.contract.FactsNetworkRepositoryContract
import dev.kigya.mindplex.feature.home.domain.model.FactDomainModel
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.statement.HttpResponse
import io.ktor.http.isSuccess
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext

class FactsNetworkRepository(
    private val httpClient: HttpClient,
    private val secretsProviderContract: SecretsProviderContract,
    private val dispatcher: CoroutineDispatcher,
) : FactsNetworkRepositoryContract {
    override suspend fun fetchFacts(amount: Int): Result<List<FactDomainModel>> =
        runSuspendCatching {
            withContext(dispatcher) {
                secretsProviderContract.provideApiKey(Document.FACTS_API).fold(
                    onSuccess = { apiKey ->
                        List(amount) {
                            async {
                                val response: HttpResponse = httpClient.get(FACTS_SOURCE) {
                                    header("X-Api-Key", apiKey)
                                }
                                if (response.status.isSuccess()) {
                                    response.body<List<FactRemoteDto>>().first()
                                } else {
                                    throw FailedToFetchFactException(response.status.description)
                                }
                            }
                        }.awaitAll().toDomain()
                    },
                    onFailure = {
                        throw FailedToProvideApiKeyException(it.message)
                    },
                )
            }
        }

    private companion object {
        const val FACTS_SOURCE = "https://api.api-ninjas.com/v1/facts"
    }
}
