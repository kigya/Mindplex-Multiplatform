plugins {
    with(libs.plugins) {
        alias(android.application)
        alias(google.services)

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
    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    with(libs) {
        implementation(compose.android.activity)
        implementation(koin.core)
        implementation(firebase.common.ktx)
    }

    with(projects) {
        implementation(shared)
    }
}
