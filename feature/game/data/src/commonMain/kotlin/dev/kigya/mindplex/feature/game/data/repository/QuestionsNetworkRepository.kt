package dev.kigya.mindplex.feature.game.data.repository

import com.mohamedrejeb.ksoup.entities.KsoupEntities
import dev.kigya.mindplex.feature.game.data.mapper.QuestionsRemoteDataMapper
import dev.kigya.mindplex.feature.game.data.mapper.QuestionsRemoteDataMapper.mappedBy
import dev.kigya.mindplex.feature.game.data.model.remote.QuestionDto
import dev.kigya.mindplex.feature.game.domain.contract.QuestionsNetworkRepositoryContract
import dev.kigya.mindplex.feature.game.domain.model.QuestionDomainModel
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json

class QuestionsNetworkRepository(
    private val httpClient: HttpClient,
    private val dispatcher: CoroutineDispatcher,
) : QuestionsNetworkRepositoryContract {

    override suspend fun getQuestions(): Result<List<QuestionDomainModel>> =
        withContext(dispatcher) {
            runCatching {
                val questionsJson: String = httpClient.get(QUESTIONS_URL).body()
                Json.decodeFromString<List<QuestionDto>>(questionsJson)
                    .map { it.decodeFields() }
                    .mappedBy(QuestionsRemoteDataMapper)
            }
        }

    private fun QuestionDto.decodeFields(): QuestionDto = copy(
        question = KsoupEntities.decodeHtml(question),
        correctAnswer = KsoupEntities.decodeHtml(correctAnswer),
        incorrectAnswers = incorrectAnswers.map(KsoupEntities::decodeHtml),
    )

    private companion object {
        const val QUESTIONS_URL = "https://gist.githubusercontent.com/kigya/" +
            "02600eb3613181172b7631f12a7e87e8/raw/7c74c9cb13fa70d042e518810c9ac6abd13ce631/" +
            "questions.json"
    }
}
