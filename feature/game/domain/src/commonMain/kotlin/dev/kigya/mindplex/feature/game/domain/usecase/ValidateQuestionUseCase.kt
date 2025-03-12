package dev.kigya.mindplex.feature.game.domain.usecase

import arrow.core.Either
import arrow.core.raise.either
import arrow.core.raise.ensure
import dev.kigya.mindplex.core.domain.interactor.base.BaseSuspendUseCase
import dev.kigya.mindplex.core.domain.interactor.model.MindplexDomainError
import dev.kigya.mindplex.feature.game.domain.contract.QuestionsDatabaseRepositoryContract
import dev.kigya.mindplex.feature.game.domain.model.QuestionDomainResult
import dev.kigya.mindplex.feature.game.domain.model.QuestionValidationDomainModel
import dev.kigya.mindplex.feature.game.domain.model.UserChoiceDomainModel
import dev.kigya.mindplex.feature.game.domain.model.ValidationType

class ValidateQuestionUseCase(
    private val localRepo: QuestionsDatabaseRepositoryContract,
) : BaseSuspendUseCase<Either<MindplexDomainError, QuestionValidationDomainModel>, UserChoiceDomainModel>() {

    override suspend operator fun invoke(
        params: UserChoiceDomainModel,
    ): Either<MindplexDomainError, QuestionValidationDomainModel> = either {
        val questionDomain = localRepo.getQuestionByText(params.question).fold(
            onSuccess = { it },
            onFailure = { raise(MindplexDomainError.OTHER) },
        )
        ensure(questionDomain != null) { MindplexDomainError.OTHER }

        val userAnswerIndex = params.answerIndex
        val correctIndex = questionDomain.correctAnswerIndex

        val results = questionDomain.answers.map { rawAnswer ->
            val index = rawAnswer.index
            val validationType = when {
                index == userAnswerIndex && index == correctIndex -> ValidationType.CORRECT
                index == userAnswerIndex && index != correctIndex -> ValidationType.INCORRECT
                index == correctIndex && index != userAnswerIndex -> ValidationType.CORRECT
                else -> ValidationType.NEUTRAL
            }
            QuestionDomainResult(index, validationType)
        }

        QuestionValidationDomainModel(
            isAnswerCorrect = results.none { it.validationType == ValidationType.INCORRECT } &&
                params.answerIndex != -1,
            question = questionDomain.question,
            results = results,
        )
    }
}
