package dev.kigya.mindplex.feature.home.domain.usecase

import dev.kigya.mindplex.core.domain.connectivity.contract.ConnectivityRepositoryContract
import dev.kigya.mindplex.core.domain.interactor.base.BaseSuspendUseCase
import dev.kigya.mindplex.core.domain.interactor.model.MindplexDomainError
import dev.kigya.mindplex.feature.home.domain.contract.FactsDatabaseRepositoryContract
import dev.kigya.mindplex.feature.home.domain.contract.FactsNetworkRepositoryContract
import dev.kigya.mindplex.feature.home.domain.model.FactDomainModel
import dev.kigya.outcome.Outcome
import dev.kigya.outcome.unwrap
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.jvm.JvmInline

class GetFactsUseCase(
    private val factsNetworkRepositoryContract: FactsNetworkRepositoryContract,
    private val factsDatabaseRepositoryContract: FactsDatabaseRepositoryContract,
    private val connectivityRepositoryContract: ConnectivityRepositoryContract,
) : BaseSuspendUseCase<Outcome<MindplexDomainError, List<FactDomainModel>>, GetFactsUseCase.Params>() {
    @JvmInline
    value class Params(val amount: Int)

    override suspend operator fun invoke(
        params: Params,
    ): Outcome<MindplexDomainError, List<FactDomainModel>> {
        val currentDate = Clock.System.now().toLocalDateTime(TimeZone.UTC).date
        return factsDatabaseRepositoryContract.getFacts().unwrap(
            onSuccess = { facts: List<FactDomainModel> ->
                if (facts.any { fact -> fact.timestamp != currentDate }) {
                    fetchRemoteFacts(params)
                } else {
                    Outcome.success(facts)
                }
            },
            onFailure = { fetchRemoteFacts(params) },
        )
    }

    private suspend fun fetchRemoteFacts(params: Params) =
        factsNetworkRepositoryContract.fetchFacts(params.amount).unwrap(
            onSuccess = {
                factsDatabaseRepositoryContract.deleteFacts()
                factsDatabaseRepositoryContract.saveFacts(it)
                Outcome.success(it)
            },
            onFailure = {
                if (!connectivityRepositoryContract.isConnected()) {
                    Outcome.failure(MindplexDomainError.NETWORK)
                }
                Outcome.failure(MindplexDomainError.OTHER)
            },
        )
}
