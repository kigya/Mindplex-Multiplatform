package dev.kigya.mindplex.core.util.extension

import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

@OptIn(ExperimentalContracts::class)
inline fun <C : Collection<*>> C.ifNotEmpty(block: C.() -> Unit): Any {
    contract {
        callsInPlace(block, InvocationKind.AT_MOST_ONCE)
    }
    return if (isNotEmpty()) block() else this
}
