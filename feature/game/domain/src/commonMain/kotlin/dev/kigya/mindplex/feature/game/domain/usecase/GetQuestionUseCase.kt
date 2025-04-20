package dev.kigya.mindplex.feature.game.domain.usecase

import dev.kigya.mindplex.core.domain.connectivity.contract.ConnectivityRepositoryContract
import dev.kigya.mindplex.core.domain.interactor.base.BaseSuspendUseCase
import dev.kigya.mindplex.core.domain.interactor.model.MindplexDomainError
import dev.kigya.mindplex.feature.game.domain.contract.QuestionsDatabaseRepositoryContract
import dev.kigya.mindplex.feature.game.domain.contract.QuestionsNetworkRepositoryContract
import dev.kigya.mindplex.feature.game.domain.model.GameDomainConfig
import dev.kigya.mindplex.feature.game.domain.model.QuestionDomainModel
import dev.kigya.outcome.Outcome
import dev.kigya.outcome.getOrElse
import dev.kigya.outcome.unwrap

class GetQuestionUseCase(
    private val questionsNetworkRepositoryContract: QuestionsNetworkRepositoryContract,
    private val questionsDatabaseRepositoryContract: QuestionsDatabaseRepositoryContract,
    private val connectivityRepositoryContract: ConnectivityRepositoryContract,
) : BaseSuspendUseCase<Outcome<MindplexDomainError, QuestionDomainModel>, GameDomainConfig>() {

    override suspend operator fun invoke(
        params: GameDomainConfig,
    ): Outcome<MindplexDomainError, QuestionDomainModel> {
        val localCount = questionsDatabaseRepositoryContract.getCount().getOrElse {
            return Outcome.failure(MindplexDomainError.OTHER)
        }

        if (localCount == 0) {
            fetchRemoteQuestions()
        }

        return questionsDatabaseRepositoryContract.getQuestion(params).unwrap(
            onSuccess = { question ->
                question?.let {
                    Outcome.success(question)
                } ?: Outcome.failure(MindplexDomainError.OTHER)
            },
            onFailure = {
                Outcome.failure(MindplexDomainError.OTHER)
            },
        )
    }

    private suspend fun fetchRemoteQuestions() = questionsNetworkRepositoryContract
        .getQuestions()
        .unwrap(
            onSuccess = { questions ->
                questionsDatabaseRepositoryContract
                    .saveQuestions(questions)
                    .getOrElse { return@unwrap Outcome.failure(MindplexDomainError.OTHER) }
            },
            onFailure = {
                if (!connectivityRepositoryContract.isConnected()) {
                    return@unwrap Outcome.failure(MindplexDomainError.NETWORK)
                }
                return@unwrap Outcome.failure(MindplexDomainError.OTHER)
            },
        )
}
