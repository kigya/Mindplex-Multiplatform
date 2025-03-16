package dev.kigya.mindplex.feature.game.domain.usecase

import arrow.core.Either
import arrow.core.raise.Raise
import arrow.core.raise.either
import arrow.core.raise.ensure
import dev.kigya.mindplex.core.domain.connectivity.contract.ConnectivityRepositoryContract
import dev.kigya.mindplex.core.domain.interactor.base.BaseSuspendUseCase
import dev.kigya.mindplex.core.domain.interactor.model.MindplexDomainError
import dev.kigya.mindplex.feature.game.domain.contract.QuestionsDatabaseRepositoryContract
import dev.kigya.mindplex.feature.game.domain.contract.QuestionsNetworkRepositoryContract
import dev.kigya.mindplex.feature.game.domain.model.GameDomainConfig
import dev.kigya.mindplex.feature.game.domain.model.QuestionDomainModel

class GetQuestionUseCase(
    private val remoteRepo: QuestionsNetworkRepositoryContract,
    private val localRepo: QuestionsDatabaseRepositoryContract,
    private val connectivityRepo: ConnectivityRepositoryContract,
) : BaseSuspendUseCase<Either<MindplexDomainError, QuestionDomainModel>, GameDomainConfig>() {

    override suspend operator fun invoke(
        params: GameDomainConfig,
    ): Either<MindplexDomainError, QuestionDomainModel> = either {
        val localCount = localRepo.getCount().getOrElse {
            raise(MindplexDomainError.OTHER)
        }

        if (localCount == 0) {
            fetchRemoteQuestions()
        }

        localRepo.getQuestion(params).fold(
            onSuccess = { question ->
                question ?: raise(MindplexDomainError.OTHER)
            },
            onFailure = {
                raise(MindplexDomainError.OTHER)
            },
        )
    }

    private suspend fun Raise<MindplexDomainError>.fetchRemoteQuestions() {
        remoteRepo.getQuestions().fold(
            onSuccess = { questions ->
                localRepo
                    .saveQuestions(questions)
                    .getOrElse { raise(MindplexDomainError.OTHER) }
            },
            onFailure = {
                ensure(connectivityRepo.isConnected().not()) { MindplexDomainError.OTHER }
                raise(MindplexDomainError.NETWORK)
            },
        )
    }
}
