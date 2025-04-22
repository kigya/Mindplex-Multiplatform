package dev.kigya.mindplex.feature.game.domain.contract

import dev.kigya.mindplex.feature.game.domain.model.QuestionDomainModel
import dev.kigya.outcome.Outcome

interface QuestionsNetworkRepositoryContract {
    suspend fun getQuestions(): Outcome<*, List<QuestionDomainModel>>
}
