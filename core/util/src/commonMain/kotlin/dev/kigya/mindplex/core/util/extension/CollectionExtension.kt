package dev.kigya.mindplex.core.util.extension

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.collections.immutable.toPersistentList
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

fun <T> ImmutableList<T>.update(
    index: Int,
    transform: (T) -> T,
): ImmutableList<T> {
    if (index !in 0 until size) return this

    return mapIndexed { i, item ->
        if (i == index) transform(item) else item
    }.toImmutableList()
}

fun <T, R> ImmutableList<T>.mapPersistent(transform: (T) -> R): PersistentList<R> =
    this.map(transform).toPersistentList()
