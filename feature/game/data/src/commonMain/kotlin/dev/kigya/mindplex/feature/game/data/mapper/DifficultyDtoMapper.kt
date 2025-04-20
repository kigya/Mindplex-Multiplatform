package dev.kigya.mindplex.feature.game.data.mapper

import dev.kigya.mindplex.core.util.mapper.DomainMapper
import dev.kigya.mindplex.feature.game.data.model.remote.DifficultyDto
import dev.kigya.mindplex.feature.game.domain.model.DifficultyDomainModel
import dev.kigya.outcome.getOrDefault
import dev.kigya.outcome.outcomeCatching

internal object DifficultyDtoMapper : DomainMapper<DifficultyDto, DifficultyDomainModel>() {

    override fun mapToDomainModel(entity: DifficultyDto): DifficultyDomainModel = outcomeCatching {
        DifficultyDomainModel.valueOf(entity.name)
    }.getOrDefault(DifficultyDomainModel.EASY)

    override fun mapFromDomainModel(domainModel: DifficultyDomainModel): DifficultyDto =
        outcomeCatching {
            DifficultyDto.valueOf(domainModel.name)
        }.getOrDefault(DifficultyDto.EASY)
}
