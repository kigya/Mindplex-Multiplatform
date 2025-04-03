package dev.kigya.mindplex.feature.login.data.repository.repository

import dev.kigya.mindplex.core.data.credentials.api.SecretsProviderContract
import dev.kigya.mindplex.core.data.firebase.FirestoreConfig.Collection.Secrets.Document
import dev.kigya.mindplex.feature.login.data.model.GeoLocationResponse
import dev.kigya.mindplex.feature.login.data.model.PublicIPResponse
import dev.kigya.mindplex.feature.login.domain.contract.GeoLocationContract
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class GeoLocationRepository(
    private val httpClient: HttpClient,
    private val secretsProviderContract: SecretsProviderContract,
) : GeoLocationContract {

    override suspend fun getUserCountryCode(): Result<String?> {
        val ipAddress = getPublicIP()
        val tokenResult = secretsProviderContract.provideToken(Document.USER_TOKEN)

        return tokenResult.fold(
            onSuccess = { token ->
                val response: GeoLocationResponse = httpClient
                    .get(USER_COUNTRY_CODE.replace("%s", ipAddress).replace("{token}", token))
                    .body()

                Result.success(response.country)
            },
            onFailure = {
                Result.failure(it)
            },
        )
    }

    private suspend fun getPublicIP(): String {
        val response = httpClient.get(USER_IP).body<PublicIPResponse>()
        return response.ip
    }

    private companion object GeoLocationConstants {
        const val USER_IP = "https://api.ipify.org?format=json"
        const val USER_COUNTRY_CODE = "https://ipinfo.io/%s?token={token}"
    }
}
