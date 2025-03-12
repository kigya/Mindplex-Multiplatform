package dev.kigya.mindplex.feature.game.data.mapper

import dev.kigya.mindplex.core.util.mapper.DomainMapper
import dev.kigya.mindplex.feature.game.data.model.remote.DifficultyDto
import dev.kigya.mindplex.feature.game.domain.model.DifficultyDomainModel

internal object DifficultyDtoMapper : DomainMapper<DifficultyDto, DifficultyDomainModel>() {

    override fun mapToDomainModel(entity: DifficultyDto): DifficultyDomainModel = runCatching {
        DifficultyDomainModel.valueOf(entity.name)
    }.getOrDefault(DifficultyDomainModel.EASY)

    override fun mapFromDomainModel(domainModel: DifficultyDomainModel): DifficultyDto =
        runCatching {
            DifficultyDto.valueOf(domainModel.name)
        }.getOrDefault(DifficultyDto.EASY)
}
