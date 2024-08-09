package dev.kigya.mindplex.core.util.dsl

import kotlinx.coroutines.TimeoutCancellationException
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract
import kotlin.coroutines.cancellation.CancellationException

@OptIn(ExperimentalContracts::class)
inline fun <T : Any> requireNotNullOrRaise(value: T?, lazyExecute: () -> Nothing): T {
    contract {
        returns() implies (value != null)
    }

    if (value == null) {
        lazyExecute()
    } else {
        return value
    }
}

@OptIn(ExperimentalContracts::class)
inline fun <T> T?.ifPresentOrElse(ifPresent: (T) -> Unit, ifAbsent: () -> Unit) {
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
inline fun <T, R> T?.ifPresentOrElseReturn(ifPresent: (T) -> R, ifAbsent: () -> R): R {
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

@Suppress("TooGenericExceptionCaught")
inline fun <R> runSuspendCatching(block: () -> R): Result<R> = try {
    Result.success(block())
} catch (t: TimeoutCancellationException) {
    Result.failure(t)
} catch (c: CancellationException) {
    throw c
} catch (e: Throwable) {
    Result.failure(e)
}

@OptIn(ExperimentalContracts::class)
inline fun <T> T.takeOrExecute(predicate: (T) -> Boolean, action: () -> T): T {
    contract {
        callsInPlace(predicate, InvocationKind.EXACTLY_ONCE)
        callsInPlace(action, InvocationKind.AT_MOST_ONCE)
    }

    return if (predicate(this)) {
        this
    } else {
        action()
    }
}
