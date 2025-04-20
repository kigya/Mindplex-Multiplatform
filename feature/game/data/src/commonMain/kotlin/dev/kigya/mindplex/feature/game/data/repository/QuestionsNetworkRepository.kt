package dev.kigya.mindplex.feature.game.data.repository

import com.mohamedrejeb.ksoup.entities.KsoupEntities
import dev.kigya.mindplex.core.data.scout.api.ScoutNetworkClientContract
import dev.kigya.mindplex.core.data.scout.api.fetchReified
import dev.kigya.mindplex.feature.game.data.mapper.QuestionsRemoteDataMapper
import dev.kigya.mindplex.feature.game.data.mapper.QuestionsRemoteDataMapper.mappedBy
import dev.kigya.mindplex.feature.game.data.model.remote.QuestionDto
import dev.kigya.mindplex.feature.game.domain.contract.QuestionsNetworkRepositoryContract
import dev.kigya.mindplex.feature.game.domain.model.QuestionDomainModel
import dev.kigya.outcome.Outcome
import dev.kigya.outcome.outcomeSuspendCatchingOn
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.serialization.json.Json

class QuestionsNetworkRepository(
    private val scoutNetworkClientContract: ScoutNetworkClientContract,
    private val dispatcher: CoroutineDispatcher,
) : QuestionsNetworkRepositoryContract {

    override suspend fun getQuestions(): Outcome<*, List<QuestionDomainModel>> =
        outcomeSuspendCatchingOn(dispatcher) {
            val questionsJson: String = scoutNetworkClientContract.fetchReified<String>(
                path = arrayOf("questions"),
            )
            Json.decodeFromString<List<QuestionDto>>(questionsJson)
                .map { it.decodeFields() }
                .mappedBy(QuestionsRemoteDataMapper)
        }

    private fun QuestionDto.decodeFields(): QuestionDto = copy(
        question = KsoupEntities.decodeHtml(question),
        correctAnswer = KsoupEntities.decodeHtml(correctAnswer),
        incorrectAnswers = incorrectAnswers.map(KsoupEntities::decodeHtml),
    )
}
