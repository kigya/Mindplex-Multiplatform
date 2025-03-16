package dev.kigya.mindplex.feature.game.domain.contract

import dev.kigya.mindplex.feature.game.domain.model.GameDomainConfig
import dev.kigya.mindplex.feature.game.domain.model.QuestionDomainModel

interface QuestionsDatabaseRepositoryContract {

    suspend fun getQuestion(config: GameDomainConfig): Result<QuestionDomainModel?>

    suspend fun getCount(): Result<Int>

    suspend fun saveQuestions(questions: List<QuestionDomainModel>): Result<Unit>

    suspend fun getQuestionByText(questionText: String): Result<QuestionDomainModel?>

    suspend fun clearAll(): Result<Unit>
}
