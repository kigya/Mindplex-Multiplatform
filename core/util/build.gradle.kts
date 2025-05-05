plugins {
    with(libs.plugins.convention) {
        alias(config.shared.library)
        alias(bundle.shared.ui.screen.compose)
        alias(component.koin)
    }
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            with(libs) {
                implementation(coroutines.core)
                implementation(compose.navigation)
                api(compose.shimmer)
                with(dataStore) {
                    implementation(core)
                    implementation(preferences)
                }
            }
        }
    }
}
