plugins {
    with(libs.plugins.convention) {
        alias(config.shared.library)
        alias(bundle.shared.ui.screen.compose)
        with(component) {
            alias(koin)
            alias(serialization)
            alias(room)
            alias(ktor)
        }
    }
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            with(libs) {
                implementation(ksoup)
                with(dataStore) {
                    implementation(preferences)
                    implementation(core)
                }
            }
            with(projects) {
                implementation(feature.game.domain)
                with(core) {
                    implementation(util)
                    with(data) {
                        implementation(firebase)
                        implementation(scout)
                    }
                }
            }
        }
    }
}
