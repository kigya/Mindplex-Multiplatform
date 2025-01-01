package dev.kigya.mindplex.core.util.extension

object Lambda {
    fun <T> noOpConsumer(): (T) -> Unit = { _ -> }
    fun empty(): () -> Unit = { }
}
