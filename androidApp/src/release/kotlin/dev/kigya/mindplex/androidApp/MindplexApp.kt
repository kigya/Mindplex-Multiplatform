package dev.kigya.mindplex.androidApp

import android.app.Application
import android.content.Context
import com.google.firebase.Firebase
import com.google.firebase.initialize
import dev.kigya.mindplex.initKoin
import org.koin.dsl.module

internal class MindplexApp : Application() {
    private val appModule = module { single<Context> { this@MindplexApp } }

    override fun onCreate() {
        super.onCreate()
        Firebase.initialize(this@MindplexApp)
        initKoin(appModule = appModule)
    }
}
