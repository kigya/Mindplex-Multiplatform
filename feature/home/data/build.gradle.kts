plugins {
    with(libs.plugins.convention) {
        alias(config.shared.library)
        alias(bundle.shared.ui.screen.compose)
        with(component) {
            alias(koin)
            alias(serialization)
            alias(room)
        }
    }
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            with(libs) {
                implementation(kotlinx.datetime)
                with(dataStore) {
                    implementation(core)
                    implementation(preferences)
                }
            }
            with(projects) {
                implementation(feature.home.domain)
                with(core) {
                    implementation(util)
                    implementation(data.scout)
                }
            }
        }
    }
}
