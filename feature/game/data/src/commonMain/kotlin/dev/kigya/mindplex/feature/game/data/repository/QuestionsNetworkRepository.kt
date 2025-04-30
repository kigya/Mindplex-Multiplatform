package dev.kigya.mindplex.feature.game.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.mohamedrejeb.ksoup.entities.KsoupEntities
import dev.kigya.mindplex.core.data.scout.api.ScoutNetworkClientContract
import dev.kigya.mindplex.core.data.scout.api.getReified
import dev.kigya.mindplex.feature.game.data.mapper.QuestionsRemoteDataMapper
import dev.kigya.mindplex.feature.game.data.mapper.QuestionsRemoteDataMapper.mappedBy
import dev.kigya.mindplex.feature.game.data.model.remote.QuestionDto
import dev.kigya.mindplex.feature.game.domain.contract.QuestionsNetworkRepositoryContract
import dev.kigya.mindplex.feature.game.domain.model.QuestionDomainModel
import dev.kigya.outcome.Outcome
import dev.kigya.outcome.outcomeSuspendCatchingOn
import io.ktor.http.HttpHeaders
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.serialization.json.Json

class QuestionsNetworkRepository(
    private val scoutNetworkClientContract: ScoutNetworkClientContract,
    private val dataStore: DataStore<Preferences>,
    private val dispatcher: CoroutineDispatcher,
) : QuestionsNetworkRepositoryContract {

    override suspend fun getQuestions(): Outcome<*, List<QuestionDomainModel>> =
        outcomeSuspendCatchingOn(dispatcher) {
            val mindplexJwt = dataStore.data.map { preferences ->
                preferences[stringPreferencesKey(MINDPLEX_JWT)]
            }.first()

            val questionsJson: String = scoutNetworkClientContract.getReified<String>(
                path = arrayOf("questions"),
                headers = mapOf(HttpHeaders.Authorization to "Bearer $mindplexJwt"),
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

    private companion object {
        const val MINDPLEX_JWT = "mindplex_jwt"
    }
}
