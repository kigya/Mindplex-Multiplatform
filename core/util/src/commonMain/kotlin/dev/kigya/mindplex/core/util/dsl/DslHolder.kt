package dev.kigya.mindplex.core.util.dsl

import arrow.core.Either
import arrow.core.Either.Companion.zipOrAccumulate
import arrow.core.NonEmptyList
import arrow.fx.coroutines.parZip
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.coroutineScope
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.coroutines.cancellation.CancellationException

@OptIn(ExperimentalContracts::class)
inline fun <T : Any> requireNotNullOrRaise(
    value: T?,
    lazyExecute: () -> Nothing,
): T {
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

inline fun <R> runSuspendCatching(block: () -> R): Result<R> = try {
    Result.success(block())
} catch (t: TimeoutCancellationException) {
    Result.failure(t)
} catch (c: CancellationException) {
    throw c
} catch (ignore: Throwable) {
    Result.failure(ignore)
}

@OptIn(ExperimentalContracts::class)
inline fun <T> T.takeOrExecute(
    predicate: (T) -> Boolean,
    action: () -> T,
): T {
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

suspend fun <E, A, B, Z> parZipOrAccumulate(
    ctx: CoroutineContext = EmptyCoroutineContext,
    fa: suspend CoroutineScope.() -> Either<E, A>,
    fb: suspend CoroutineScope.() -> Either<E, B>,
    onSuccess: (A, B) -> Z,
    onError: (NonEmptyList<E>) -> Unit,
) = coroutineScope {
    parZip(
        ctx = ctx,
        fa = { fa() },
        fb = { fb().mapLeft { it } },
    ) { eitherA, eitherB ->
        zipOrAccumulate(eitherA, eitherB, onSuccess)
    }.fold(
        ifLeft = { errorList -> onError(errorList) },
        ifRight = { },
    )
}
