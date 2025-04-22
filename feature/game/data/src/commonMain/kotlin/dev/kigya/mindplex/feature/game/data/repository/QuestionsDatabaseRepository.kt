package dev.kigya.mindplex.feature.game.data.repository

import dev.kigya.mindplex.feature.game.data.dao.QuestionDao
import dev.kigya.mindplex.feature.game.data.mapper.CategoryLocalMapper
import dev.kigya.mindplex.feature.game.data.mapper.DifficultyLocalMapper
import dev.kigya.mindplex.feature.game.data.mapper.QuestionLocalDomainMapper
import dev.kigya.mindplex.feature.game.data.mapper.TypeLocalMapper
import dev.kigya.mindplex.feature.game.data.mapper.TypeLocalMapper.mappedBy
import dev.kigya.mindplex.feature.game.domain.contract.QuestionsDatabaseRepositoryContract
import dev.kigya.mindplex.feature.game.domain.model.GameDomainConfig
import dev.kigya.mindplex.feature.game.domain.model.QuestionDomainModel
import dev.kigya.outcome.Outcome
import dev.kigya.outcome.outcomeSuspendCatchingOn
import kotlinx.coroutines.CoroutineDispatcher

class QuestionsDatabaseRepository(
    private val questionDao: QuestionDao,
    private val dispatcher: CoroutineDispatcher,
) : QuestionsDatabaseRepositoryContract {

    override suspend fun getQuestion(config: GameDomainConfig): Outcome<*, QuestionDomainModel?> =
        outcomeSuspendCatchingOn(dispatcher) {
            val type = config.type mappedBy TypeLocalMapper
            val difficulty = config.difficulty mappedBy DifficultyLocalMapper
            val category = config.category mappedBy CategoryLocalMapper

            val local = questionDao.getQuestion(type, difficulty, category)
            local?.let { it mappedBy QuestionLocalDomainMapper }
        }

    override suspend fun getCount(): Outcome<*, Int> = outcomeSuspendCatchingOn(dispatcher) {
        questionDao.getCount()
    }

    override suspend fun saveQuestions(questions: List<QuestionDomainModel>): Outcome<*, Unit> =
        outcomeSuspendCatchingOn(dispatcher) {
            val localQuestions = questions.map { question ->
                question mappedBy QuestionLocalDomainMapper
            }
            questionDao.insertQuestions(localQuestions)
        }

    override suspend fun getQuestionByText(questionText: String): Outcome<*, QuestionDomainModel?> =
        outcomeSuspendCatchingOn(dispatcher) {
            val local = questionDao.getQuestionByText(questionText)
            local?.let { it mappedBy QuestionLocalDomainMapper }
        }

    override suspend fun clearAll(): Outcome<*, Unit> = outcomeSuspendCatchingOn(dispatcher) {
        questionDao.clearAll()
    }
}
