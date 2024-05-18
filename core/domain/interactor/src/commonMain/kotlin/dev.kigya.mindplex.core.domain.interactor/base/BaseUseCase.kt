package dev.kigya.mindplex.core.domain.interactor.base

/**
 * Abstract class for defining a use case that performs operations synchronously.
 *
 * This class provides a structured way to define use cases that directly return a result
 * without the need for asynchronous operations. It is ideal for operations that are
 * computationally simple or already provide their results in a prompt manner.
 *
 * @param ReturnType the type of result the use case will return. This must be a non-nullable type.
 * @param Params the type of parameters the use case accepts.
 */
abstract class BaseUseCase<out ReturnType, in Params> where ReturnType : Any {
    /**
     * Invokes the use case with the given parameters.
     *
     * @param params the parameters required to execute the use case.
     * @return the result of the use case operation.
     */
    abstract operator fun invoke(params: Params): ReturnType
}
