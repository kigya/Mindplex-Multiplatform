package dev.kigya.mindplex.feature.game.data.model.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class QuestionDto(
    @SerialName("type") val type: TypeDto,
    @SerialName("difficulty") val difficulty: DifficultyDto,
    @SerialName("category") val category: CategoryDto,
    @SerialName("question") val question: String,
    @SerialName("correct_answer") val correctAnswer: String,
    @SerialName("incorrect_answers") val incorrectAnswers: List<String>,
)
