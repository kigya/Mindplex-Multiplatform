package dev.kigya.mindplex.core.domain.interactor.base

abstract class BaseUseCase<out ReturnType, in Params> where ReturnType : Any {
    abstract operator fun invoke(params: Params): ReturnType
}
