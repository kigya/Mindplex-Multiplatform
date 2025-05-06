package dev.kigya.mindplex.core.util.buildstage

import dev.kigya.mindplex.build.BuildKonfig

class BuildStageProvider : BuildStageContract {
    override fun getStage(): String = BuildKonfig.BUILD_TYPE
}
