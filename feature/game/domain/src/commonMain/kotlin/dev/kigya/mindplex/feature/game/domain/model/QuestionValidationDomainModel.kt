package dev.kigya.mindplex.feature.game.domain.model

data class QuestionValidationDomainModel(
    val question: String,
    val validationType: ValidationType,
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
    NO_ANSWER,
}
