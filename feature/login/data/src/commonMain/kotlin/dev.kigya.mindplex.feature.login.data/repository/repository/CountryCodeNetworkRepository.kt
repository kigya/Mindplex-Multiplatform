package dev.kigya.mindplex.feature.login.data.repository.repository

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.firestore
import dev.kigya.mindplex.core.data.scout.api.ScoutNetworkClientContract
import dev.kigya.mindplex.core.data.scout.api.fetchReified
import dev.kigya.mindplex.feature.login.data.mapper.CountryCodeRemoteMapper
import dev.kigya.mindplex.feature.login.data.mapper.CountryCodeRemoteMapper.mappedBy
import dev.kigya.mindplex.feature.login.data.model.CountryCodeResponse
import dev.kigya.mindplex.feature.login.domain.contract.CountryCodeNetworkRepositoryContract
import dev.kigya.outcome.Outcome
import dev.kigya.outcome.outcomeSuspendCatchingOn
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import dev.kigya.mindplex.core.data.firebase.FirestoreConfig.Collection.Users as UsersCollection

class CountryCodeNetworkRepository(
    private val scoutNetworkClientContract: ScoutNetworkClientContract,
    private val dispatcher: CoroutineDispatcher,
) : CountryCodeNetworkRepositoryContract {

    override suspend fun fetchCountryCode(): Outcome<*, String> =
        outcomeSuspendCatchingOn(dispatcher) {
            scoutNetworkClientContract.fetchReified<CountryCodeResponse>(
                path = arrayOf("country"),
            ) mappedBy CountryCodeRemoteMapper
        }

    override suspend fun updateCountryCode(
        userId: String,
        countryCode: String,
    ) = withContext(dispatcher) {
        val documentRef = Firebase.firestore
            .collection(UsersCollection.NAME)
            .document(userId)

        documentRef.update(
            hashMapOf(
                UsersCollection.Document.COUNTRY_CODE to countryCode,
            ),
        )
    }
}
