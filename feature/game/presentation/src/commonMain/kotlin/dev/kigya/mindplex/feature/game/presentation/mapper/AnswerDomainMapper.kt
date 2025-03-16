package dev.kigya.mindplex.feature.game.presentation.mapper

import dev.kigya.mindplex.core.util.mapper.DomainMapper
import dev.kigya.mindplex.feature.game.domain.model.RawAnswerDomainModel
import dev.kigya.mindplex.feature.game.presentation.contract.GameContract
import dev.kigya.mindplex.feature.game.presentation.contract.GameContract.State.Answer.DisplayType

internal object AnswerDomainMapper :
    DomainMapper<GameContract.State.Answer, RawAnswerDomainModel>() {

    override fun mapFromDomainModel(domainModel: RawAnswerDomainModel): GameContract.State.Answer =
        GameContract.State.Answer(
            text = domainModel.text,
            displayType = DisplayType.NEUTRAL,
        )
}
