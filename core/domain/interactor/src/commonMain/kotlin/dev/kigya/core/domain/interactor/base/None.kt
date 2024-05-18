package dev.kigya.core.domain.interactor.base

/**
 * A utility object used in cases where no input parameters are required for a function or method.
 *
 * `None` serves as a placeholder to signify the absence of parameters in scenarios where a
 * method signature requires an argument.
 *
 * How to Use:
 * You can use `None` as a parameter type when defining use cases or functions that do not require input.
 *
 * ```kotlin
 * class InitializeApplicationUseCase : BaseUseCase<Boolean, None>() {
 *     override fun invoke(params: None): Boolean {
 *         ...
 *         return true
 *     }
 * }
 * ```
 *
 * In this example, `InitializeApplicationUseCase` is a use case that does not require any input to
 * execute its logic but is implemented in a system where use cases typically expect parameters.
 */
object None
