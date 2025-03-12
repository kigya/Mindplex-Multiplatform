package dev.kigya.mindplex.feature.game.domain.model

data class UserChoiceDomainModel(
    val isAnswered: Boolean,
    val question: String,
    val answerIndex: Int,
)
