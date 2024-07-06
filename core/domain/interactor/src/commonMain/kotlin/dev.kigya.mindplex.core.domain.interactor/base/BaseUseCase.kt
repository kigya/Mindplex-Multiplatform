package dev.kigya.mindplex.core.domain.interactor.base

import androidx.annotation.AnyThread

abstract class BaseUseCase<out ReturnType, in Params> where ReturnType : Any {
    @AnyThread
    abstract operator fun invoke(params: Params): ReturnType
}
