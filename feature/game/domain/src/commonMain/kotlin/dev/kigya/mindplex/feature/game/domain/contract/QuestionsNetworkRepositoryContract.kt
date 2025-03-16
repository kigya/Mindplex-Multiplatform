package dev.kigya.mindplex.feature.game.domain.contract

import dev.kigya.mindplex.feature.game.domain.model.QuestionDomainModel

interface QuestionsNetworkRepositoryContract {
    suspend fun getQuestions(): Result<List<QuestionDomainModel>>
}
