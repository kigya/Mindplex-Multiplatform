package dev.kigya.mindplex.androidApp

import android.app.Application
import android.content.Context
import dev.kigya.mindplex.initKoin
import org.koin.dsl.module

internal class MindplexApp : Application() {
    private val appModule = module { single<Context> { this@MindplexApp } }

    override fun onCreate() {
        super.onCreate()
        initKoin(appModule = appModule)
    }
}
