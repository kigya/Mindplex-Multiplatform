package dev.kigya.mindplex

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform