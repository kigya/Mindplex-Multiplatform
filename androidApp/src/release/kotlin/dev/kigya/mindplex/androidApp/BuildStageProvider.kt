package dev.kigya.mindplex.androidApp

import dev.kigya.mindplex.core.data.scout.api.StageProvider

private const val RELEASE = "release"

class BuildStageProvider : StageProvider {
    override fun getStage(): String = RELEASE
}
