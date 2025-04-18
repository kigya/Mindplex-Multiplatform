package dev.kigya.mindplex.feature.login.data.mapper

import dev.kigya.mindplex.core.util.mapper.DomainMapper
import dev.kigya.mindplex.feature.login.data.model.CountryCodeResponse

internal object CountryCodeRemoteMapper : DomainMapper<CountryCodeResponse, String?>() {

    override fun mapToDomainModel(entity: CountryCodeResponse): String? = entity.countryCode
}
