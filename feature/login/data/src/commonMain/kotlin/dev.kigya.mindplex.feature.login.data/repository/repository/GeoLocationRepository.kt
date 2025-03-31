package dev.kigya.mindplex.feature.login.data.repository.repository

import dev.kigya.mindplex.feature.login.data.HttpClientProvider
import dev.kigya.mindplex.feature.login.data.exception.GeoLocationNotFoundException
import dev.kigya.mindplex.feature.login.data.model.GeoLocationResponse
import dev.kigya.mindplex.feature.login.data.model.PublicIPResponse
import dev.kigya.mindplex.feature.login.domain.contract.GeoLocationContract
import io.ktor.client.call.body
import io.ktor.client.request.get

class GeoLocationRepository(
    httpClientProvider: HttpClientProvider,
) : GeoLocationContract {
    private val client = httpClientProvider.create()

    override suspend fun getUserCountryCode(): String? = try {
        val ipAddress = getPublicIP()
        val response: GeoLocationResponse = client
            .get(USER_COUNTRY_CODE.replace("%s", ipAddress))
            .body()

        response.country
    } catch (e: GeoLocationNotFoundException) {
        println("GeoLocationNotFoundException: ${e.message}")
        throw e
    }

    private suspend fun getPublicIP(): String {
        val response = client.get(USER_IP).body<PublicIPResponse>()
        return response.ip
    }

    private companion object GeoLocationConstants {
        const val USER_IP = "https://api.ipify.org?format=json"
        const val USER_COUNTRY_CODE = "https://ipinfo.io/%s?token=eed469142198e0"
    }
}
