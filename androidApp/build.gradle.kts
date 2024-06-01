plugins {
    with(libs.plugins) {
        alias(android.application)

        with(convention) {
            alias(config.android)
            alias(bundle.android.ui.screen.compose)
        }
    }
}

android {
    defaultConfig {
        applicationId = "dev.kigya.mindplex"
        versionCode = 1
        versionName = "1.0"
    }
}

dependencies {
    with(libs) {
        implementation(compose.android.activity)
        implementation(koin.core)
    }

    with(projects) {
        implementation(shared)
    }
}
