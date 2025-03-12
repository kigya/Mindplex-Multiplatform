package dev.kigya.mindplex.feature.game.domain.model

data class QuestionValidationDomainModel(
    val isAnswerCorrect: Boolean,
    val question: String,
    val results: List<QuestionDomainResult>,
)

data class QuestionDomainResult(
    val index: Int,
    val validationType: ValidationType,
)

enum class ValidationType {
    INCORRECT,
    NEUTRAL,
    CORRECT,
}
