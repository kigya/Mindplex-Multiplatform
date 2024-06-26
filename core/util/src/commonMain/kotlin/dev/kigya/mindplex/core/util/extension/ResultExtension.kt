package dev.kigya.mindplex.core.util.extension

import kotlinx.coroutines.TimeoutCancellationException
import kotlin.coroutines.cancellation.CancellationException

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
