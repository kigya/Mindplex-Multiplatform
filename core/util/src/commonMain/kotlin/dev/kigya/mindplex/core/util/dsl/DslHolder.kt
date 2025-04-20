package dev.kigya.mindplex.core.util.dsl

import dev.kigya.outcome.getOrDefault
import dev.kigya.outcome.outcomeCatching
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

@OptIn(ExperimentalContracts::class)
inline fun <T> T?.ifPresentOrElse(
    ifPresent: (T) -> Unit,
    ifAbsent: () -> Unit,
) {
    contract {
        callsInPlace(ifPresent, InvocationKind.AT_MOST_ONCE)
        callsInPlace(ifAbsent, InvocationKind.AT_MOST_ONCE)
    }

    if (this != null) {
        ifPresent(this)
    } else {
        ifAbsent()
    }
}

@OptIn(ExperimentalContracts::class)
inline fun <T, R> T?.ifPresentOrElseReturn(
    ifPresent: (T) -> R,
    ifAbsent: () -> R,
): R {
    contract {
        callsInPlace(ifPresent, InvocationKind.AT_MOST_ONCE)
        callsInPlace(ifAbsent, InvocationKind.AT_MOST_ONCE)
    }

    return if (this != null) {
        ifPresent(this)
    } else {
        ifAbsent()
    }
}

@OptIn(ExperimentalContracts::class)
inline fun <T1, T2, R> invokeIfPresent(
    p1: T1?,
    p2: T2?,
    block: (T1, T2) -> R,
): R? {
    contract {
        callsInPlace(block, InvocationKind.AT_MOST_ONCE)
        returnsNotNull() implies (p1 != null && p2 != null)
    }
    return if (p1 != null && p2 != null) {
        block(p1, p2)
    } else {
        null
    }
}

inline fun <reified T : Enum<T>> safeValueOf(
    name: String,
    default: T,
): T = outcomeCatching { enumValueOf<T>(name) }.getOrDefault(default)
