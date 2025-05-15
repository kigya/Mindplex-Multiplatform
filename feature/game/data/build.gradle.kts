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
                implementation(ksoup)
            }
            with(projects) {
                implementation(feature.game.domain)
                with(core) {
                    implementation(util)
                    implementation(data.scout)
                }
            }
        }
    }
}
