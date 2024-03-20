plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.convention.config.android)
    alias(libs.plugins.convention.component.android.compose)
}

android {
    namespace = "dev.kigya.mindplex.android"
    defaultConfig {
        applicationId = "dev.kigya.mindplex.android"
        versionCode = 1
        versionName = "1.0"
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true
        }
    }
}

dependencies {
    implementation(projects.shared)

    implementation(libs.compose.android.activity)

    debugImplementation(libs.compose.android.ui.tooling)
}
