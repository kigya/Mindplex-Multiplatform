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
                implementation(compose.navigation)
            }
            with(projects) {
                with(core) {
                    implementation(presentation.theme)
                    implementation(util)
                }
            }
        }
    }
}
