package dev.kigya.mindplex.feature.game.domain.usecase

import dev.kigya.mindplex.core.domain.interactor.base.BaseSuspendUseCase
import dev.kigya.mindplex.core.domain.interactor.model.MindplexDomainError
import dev.kigya.mindplex.feature.game.domain.contract.QuestionsDatabaseRepositoryContract
import dev.kigya.mindplex.feature.game.domain.model.QuestionDomainResult
import dev.kigya.mindplex.feature.game.domain.model.QuestionValidationDomainModel
import dev.kigya.mindplex.feature.game.domain.model.UserChoiceDomainModel
import dev.kigya.mindplex.feature.game.domain.model.ValidationType
import dev.kigya.outcome.Outcome
import dev.kigya.outcome.mapFailure
import dev.kigya.outcome.unwrap

class ValidateQuestionUseCase(
    private val localRepo: QuestionsDatabaseRepositoryContract,
) : BaseSuspendUseCase<Outcome<MindplexDomainError, QuestionValidationDomainModel>, UserChoiceDomainModel>() {

    override suspend fun invoke(
        params: UserChoiceDomainModel,
    ): Outcome<MindplexDomainError, QuestionValidationDomainModel> {
        return localRepo.getQuestionByText(params.question)
            .mapFailure { MindplexDomainError.OTHER }
            .unwrap(
                onFailure = { error ->
                    return Outcome.failure(error)
                },
                onSuccess = { questionDomainNullable ->
                    val questionDomain = questionDomainNullable
                        ?: return Outcome.failure(MindplexDomainError.OTHER)

                    val userAnswerIndex = params.answerIndex
                    val correctIndex = questionDomain.correctAnswerIndex

                    val results = questionDomain.answers.map { rawAnswer ->
                        val idx = rawAnswer.index
                        val vt = when {
                            idx == userAnswerIndex && idx == correctIndex -> ValidationType.CORRECT
                            idx == userAnswerIndex && idx != correctIndex -> ValidationType.INCORRECT
                            idx == correctIndex && idx != userAnswerIndex -> ValidationType.CORRECT
                            else -> ValidationType.NEUTRAL
                        }
                        QuestionDomainResult(idx, vt)
                    }

                    val validation = QuestionValidationDomainModel(
                        isAnswerCorrect = results.none { it.validationType == ValidationType.INCORRECT } &&
                            userAnswerIndex != -1,
                        question = questionDomain.question,
                        results = results,
                    )

                    Outcome.success(validation)
                },
            )
    }
}
