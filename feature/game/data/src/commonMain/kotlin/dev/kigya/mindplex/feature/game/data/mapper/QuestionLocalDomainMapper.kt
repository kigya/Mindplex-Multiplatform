package dev.kigya.mindplex.feature.game.data.mapper

import dev.kigya.mindplex.core.util.mapper.DomainMapper
import dev.kigya.mindplex.feature.game.data.model.local.QuestionLocal
import dev.kigya.mindplex.feature.game.domain.model.GameDomainConfig
import dev.kigya.mindplex.feature.game.domain.model.QuestionDomainModel
import dev.kigya.mindplex.feature.game.domain.model.RawAnswerDomainModel

internal object QuestionLocalDomainMapper : DomainMapper<QuestionLocal, QuestionDomainModel>() {

    override fun mapToDomainModel(entity: QuestionLocal): QuestionDomainModel {
        val domainAnswers = entity.answers.mapIndexed { index, answer ->
            RawAnswerDomainModel(
                index = index,
                text = answer,
            )
        }
        return QuestionDomainModel(
            question = entity.question,
            answers = domainAnswers,
            correctAnswerIndex = entity.correctAnswerIndex,
            config = GameDomainConfig(
                category = entity.category mappedBy CategoryLocalMapper,
                difficulty = entity.difficulty mappedBy DifficultyLocalMapper,
                type = entity.type mappedBy TypeLocalMapper,
            ),
        )
    }

    override fun mapFromDomainModel(domainModel: QuestionDomainModel): QuestionLocal =
        QuestionLocal(
            question = domainModel.question,
            answers = domainModel.answers.map { it.text },
            correctAnswerIndex = domainModel.correctAnswerIndex,
            correctAnswer = domainModel.answers
                .firstOrNull { it.index == domainModel.correctAnswerIndex }
                ?.text
                .orEmpty(),
            difficulty = domainModel.config.difficulty mappedBy DifficultyLocalMapper,
            type = domainModel.config.type mappedBy TypeLocalMapper,
            category = domainModel.config.category mappedBy CategoryLocalMapper,
        )
}
