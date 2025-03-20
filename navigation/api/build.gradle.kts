plugins {
    with(libs.plugins.convention) {
        alias(config.shared.library)
        alias(bundle.shared.ui.screen.compose)
        alias(component.serialization)
    }
}

kotlin {
    sourceSets {
        androidMain.dependencies {
            with(libs) {
                implementation(libs.compose.android.activity)
            }
        }

        commonMain.dependencies {
            with(libs) {
                implementation(coroutines.core)
                implementation(compose.navigation)
            }
        }
    }
}
