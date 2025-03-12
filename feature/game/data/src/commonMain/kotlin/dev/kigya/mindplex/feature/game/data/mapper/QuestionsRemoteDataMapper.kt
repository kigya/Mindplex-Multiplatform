package dev.kigya.mindplex.feature.game.data.mapper

import com.mohamedrejeb.ksoup.entities.KsoupEntities
import dev.kigya.mindplex.core.util.mapper.DomainMapper
import dev.kigya.mindplex.feature.game.data.model.remote.QuestionDto
import dev.kigya.mindplex.feature.game.domain.model.GameDomainConfig
import dev.kigya.mindplex.feature.game.domain.model.QuestionDomainModel
import dev.kigya.mindplex.feature.game.domain.model.RawAnswerDomainModel

internal object QuestionsRemoteDataMapper :
    DomainMapper<List<QuestionDto>, List<QuestionDomainModel>>() {

    override fun mapToDomainModel(entity: List<QuestionDto>): List<QuestionDomainModel> =
        entity.map { dto ->
            val allAnswers = buildList {
                add(KsoupEntities.decodeHtml(dto.correctAnswer))
                addAll(dto.incorrectAnswers)
            }.shuffled()

            val correctIndex = allAnswers.indexOf(dto.correctAnswer)

            val domainAnswers = allAnswers.mapIndexed { index, answer ->
                RawAnswerDomainModel(
                    index = index,
                    text = answer,
                )
            }

            QuestionDomainModel(
                question = dto.question,
                answers = domainAnswers,
                correctAnswerIndex = correctIndex,
                config = GameDomainConfig(
                    category = dto.category mappedBy CategoryDtoMapper,
                    difficulty = dto.difficulty mappedBy DifficultyDtoMapper,
                    type = dto.type mappedBy TypeDtoMapper,
                ),
            )
        }
}
