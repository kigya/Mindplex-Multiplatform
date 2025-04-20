package dev.kigya.mindplex.feature.game.domain.contract

import dev.kigya.mindplex.feature.game.domain.model.GameDomainConfig
import dev.kigya.mindplex.feature.game.domain.model.QuestionDomainModel
import dev.kigya.outcome.Outcome

interface QuestionsDatabaseRepositoryContract {

    suspend fun getQuestion(config: GameDomainConfig): Outcome<*, QuestionDomainModel?>

    suspend fun getCount(): Outcome<*, Int>

    suspend fun saveQuestions(questions: List<QuestionDomainModel>): Outcome<*, Unit>

    suspend fun getQuestionByText(questionText: String): Outcome<*, QuestionDomainModel?>

    suspend fun clearAll(): Outcome<*, Unit>
}
