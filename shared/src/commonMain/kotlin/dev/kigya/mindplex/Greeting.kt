package dev.kigya.mindplex

class Greeting {
    private val platform: Platform = getPlatform()

    fun greet(): String {
        return "Hello, ${platform.name}!"
    }

    private fun Const(): String { // ToDo detekt doesn't detect
        return R_CONST
    }

    companion object {
        private const val R_CONST = "asas"
    }
}