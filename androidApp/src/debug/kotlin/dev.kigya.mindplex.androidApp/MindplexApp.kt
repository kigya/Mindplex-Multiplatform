package dev.kigya.mindplex.androidApp

import android.app.Application
import android.content.Context
import android.os.StrictMode
import com.google.firebase.Firebase
import com.google.firebase.initialize
import dev.kigya.mindplex.core.data.scout.api.StageProvider
import dev.kigya.mindplex.initKoin
import org.koin.dsl.module

internal class MindplexApp : Application() {
    private val stageProvider: StageProvider = BuildStageProvider()
    private val appModule = module {
        single<Context> { this@MindplexApp }
        single<StageProvider> { stageProvider }
    }

    override fun onCreate() {
        super.onCreate()
        StrictMode.setThreadPolicy(
            StrictMode.ThreadPolicy.Builder()
                .detectAll()
                .penaltyLog()
                .build(),
        )
        StrictMode.setVmPolicy(
            StrictMode.VmPolicy.Builder()
                .detectAll()
                .penaltyLog()
                .build(),
        )
        Firebase.initialize(this@MindplexApp)
        initKoin(appModule = appModule)
    }
}
