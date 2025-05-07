package dev.kigya.mindplex.core.data.scout.impl

import kotlin.jvm.JvmInline

sealed interface ScoutHeaders {
    @JvmInline
    value class GoogleJwt(val value: String) : ScoutHeaders

    data object MindplexJwt : ScoutHeaders
}
