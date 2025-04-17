package dev.kigya.mindplex.feature.login.data.repository.repository

import dev.kigya.mindplex.core.data.scout.api.ScoutNetworkClientContract
import dev.kigya.mindplex.core.data.scout.api.fetchReified
import dev.kigya.mindplex.core.util.dsl.runSuspendCatching
import dev.kigya.mindplex.feature.login.data.mapper.CountryCodeRemoteMapper
import dev.kigya.mindplex.feature.login.data.mapper.CountryCodeRemoteMapper.mappedBy
import dev.kigya.mindplex.feature.login.data.model.CountryCodeResponse
import dev.kigya.mindplex.feature.login.domain.contract.GeoLocationContract
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class GeoLocationRepository(
    private val scoutNetworkClientContract: ScoutNetworkClientContract,
    private val dispatcher: CoroutineDispatcher,
) : GeoLocationContract {

    override suspend fun getUserCountryCode(): Result<String?> = runSuspendCatching {
        withContext(dispatcher) {
            scoutNetworkClientContract.fetchReified<CountryCodeResponse>(
                path = arrayOf("country"),
            ) mappedBy CountryCodeRemoteMapper
        }
    }
}
