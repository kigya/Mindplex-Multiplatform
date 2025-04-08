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
    private val questionsNetworkRepositoryContract: QuestionsNetworkRepositoryContract,
    private val questionsDatabaseRepositoryContract: QuestionsDatabaseRepositoryContract,
    private val connectivityRepositoryContract: ConnectivityRepositoryContract,
) : BaseSuspendUseCase<Either<MindplexDomainError, QuestionDomainModel>, GameDomainConfig>() {

    override suspend operator fun invoke(
        params: GameDomainConfig,
    ): Either<MindplexDomainError, QuestionDomainModel> = either {
        val localCount = questionsDatabaseRepositoryContract.getCount().getOrElse {
            raise(MindplexDomainError.OTHER)
        }

        if (localCount == 0) {
            fetchRemoteQuestions()
        }

        questionsDatabaseRepositoryContract.getQuestion(params).fold(
            onSuccess = { question ->
                question ?: raise(MindplexDomainError.OTHER)
            },
            onFailure = {
                raise(MindplexDomainError.OTHER)
            },
        )
    }

    private suspend fun Raise<MindplexDomainError>.fetchRemoteQuestions() {
        questionsNetworkRepositoryContract.getQuestions().fold(
            onSuccess = { questions ->
                questionsDatabaseRepositoryContract
                    .saveQuestions(questions)
                    .getOrElse { raise(MindplexDomainError.OTHER) }
            },
            onFailure = {
                ensure(connectivityRepositoryContract.isConnected().not()) { MindplexDomainError.OTHER }
                raise(MindplexDomainError.NETWORK)
            },
        )
    }
}
