package dev.kigya.mindplex.di.internal.module

import coil3.ImageLoader
import coil3.PlatformContext
import coil3.request.crossfade
import coil3.util.DebugLogger
import org.koin.dsl.module

val libsModule = module {
    factory { (context: PlatformContext) ->
        ImageLoader.Builder(context)
            .crossfade(true)
            .logger(DebugLogger())
            .build()
    }
}
