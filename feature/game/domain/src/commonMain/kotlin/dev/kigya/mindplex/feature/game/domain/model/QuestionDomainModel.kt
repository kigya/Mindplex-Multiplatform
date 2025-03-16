package dev.kigya.mindplex.feature.game.domain.model

data class QuestionDomainModel(
    val question: String,
    val answers: List<RawAnswerDomainModel>,
    val correctAnswerIndex: Int,
    val config: GameDomainConfig,
)

data class RawAnswerDomainModel(
    val index: Int,
    val text: String,
)
