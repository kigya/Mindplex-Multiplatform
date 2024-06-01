package dev.kigya.mindplex.core.util.contract

import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract

@OptIn(ExperimentalContracts::class)
fun <T> enforceNonNullSmartCast(data: T?): T? {
    contract { returnsNotNull() implies (data != null) }
    return data
}