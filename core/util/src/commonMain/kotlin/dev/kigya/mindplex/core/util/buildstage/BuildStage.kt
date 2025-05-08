package dev.kigya.mindplex.core.util.buildstage

import dev.kigya.mindplex.build.BuildKonfig

enum class BuildStage(val rawValue: String) {
    DEBUG("debug"),
    RELEASE("release"),
    ;

    companion object {
        fun fromValue(rawValue: String): BuildStage = entries.first { it.rawValue == rawValue }
        fun current(): BuildStage = fromValue(BuildKonfig.BUILD_TYPE)
    }
}
