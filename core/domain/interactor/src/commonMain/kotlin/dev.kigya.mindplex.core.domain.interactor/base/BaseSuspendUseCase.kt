package dev.kigya.mindplex.core.domain.interactor.base

abstract class BaseSuspendUseCase<out ReturnType, in Params> where ReturnType : Any {
    abstract suspend operator fun invoke(params: Params): ReturnType
}
