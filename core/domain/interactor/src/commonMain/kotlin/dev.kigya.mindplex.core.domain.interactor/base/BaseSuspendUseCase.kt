package dev.kigya.mindplex.core.domain.interactor.base

/**
 * Abstract class for defining a use case that performs operations asynchronously.
 *
 * This class provides a structured way to define use cases that need to run operations
 * asynchronously and return a result. The operations are wrapped in a suspend function
 * which makes it compatible with Kotlin's coroutines for managing background tasks efficiently.
 *
 * @param ReturnType the type of result the use case will return. This must be a non-nullable type.
 * @param Params the type of parameters the use case accepts.
 */
abstract class BaseSuspendUseCase<out ReturnType, in Params> where ReturnType : Any {
    /**
     * Invokes the use case with the given parameters.
     *
     * @param params the parameters required to execute the use case.
     * @return the result of the use case operation.
     */
    abstract suspend operator fun invoke(params: Params): ReturnType
}
