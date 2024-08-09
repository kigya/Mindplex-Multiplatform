package dev.kigya.mindplex.feature.home.domain.model

import kotlinx.datetime.LocalDate

data class FactDomainModel(
    val fact: String,
    val timestamp: LocalDate? = null,
)
