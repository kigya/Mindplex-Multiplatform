package dev.kigya.mindplex.feature.home.domain.usecase

import arrow.core.Either
import arrow.core.raise.Raise
import arrow.core.raise.either
import arrow.core.raise.ensure
import dev.kigya.mindplex.core.domain.connectivity.contract.ConnectivityRepositoryContract
import dev.kigya.mindplex.core.domain.interactor.base.BaseSuspendUseCase
import dev.kigya.mindplex.core.domain.interactor.model.MindplexDomainError
import dev.kigya.mindplex.core.util.dsl.takeOrExecute
import dev.kigya.mindplex.feature.home.domain.contract.FactsDatabaseRepositoryContract
import dev.kigya.mindplex.feature.home.domain.contract.FactsNetworkRepositoryContract
import dev.kigya.mindplex.feature.home.domain.model.FactDomainModel
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.jvm.JvmInline

class GetFactsUseCase(
    private val factsNetworkRepositoryContract: FactsNetworkRepositoryContract,
    private val factsDatabaseRepositoryContract: FactsDatabaseRepositoryContract,
    private val connectivityRepositoryContract: ConnectivityRepositoryContract,
) : BaseSuspendUseCase<Either<MindplexDomainError, List<FactDomainModel>>, GetFactsUseCase.Params>() {
    @JvmInline
    value class Params(val amount: Int)

    override suspend operator fun invoke(
        params: Params,
    ): Either<MindplexDomainError, List<FactDomainModel>> = either {
        val currentDate = Clock.System.now().toLocalDateTime(TimeZone.UTC).date
        factsDatabaseRepositoryContract.getFacts().fold(
            onSuccess = { facts ->
                facts.takeOrExecute(
                    predicate = { it.all { fact -> fact.timestamp == currentDate } },
                    action = { fetchRemoteFacts(params) },
                )
            },
            onFailure = { fetchRemoteFacts(params) },
        )
    }

    private suspend fun Raise<MindplexDomainError>.fetchRemoteFacts(params: Params) =
        factsNetworkRepositoryContract.fetchFacts(params.amount).fold(
            onSuccess = {
                factsDatabaseRepositoryContract.deleteFacts()
                factsDatabaseRepositoryContract.saveFacts(it)
                it
            },
            onFailure = {
                ensure(connectivityRepositoryContract.isConnected().not()) { MindplexDomainError.OTHER }
                raise(MindplexDomainError.NETWORK)
            },
        )
}
