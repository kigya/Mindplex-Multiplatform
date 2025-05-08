package dev.kigya.mindplex.feature.game.data.model.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class QuestionDto(
    @SerialName("type") val type: TypeDto,
    @SerialName("difficulty") val difficulty: DifficultyDto,
    @SerialName("category") val category: CategoryDto,
    @SerialName("question") val question: String,
    @SerialName("correctAnswer") val correctAnswer: String,
    @SerialName("incorrectAnswers") val incorrectAnswers: List<String>,
)
