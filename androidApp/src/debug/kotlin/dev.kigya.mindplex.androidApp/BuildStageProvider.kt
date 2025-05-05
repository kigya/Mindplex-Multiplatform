package dev.kigya.mindplex.androidApp

import dev.kigya.mindplex.core.data.scout.api.StageProvider

private const val DEBUG = "debug"

class BuildStageProvider : StageProvider {
    override fun getStage(): String = DEBUG
}
