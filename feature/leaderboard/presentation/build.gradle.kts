plugins {
    with(libs.plugins.convention) {
        alias(config.shared.library)
        alias(bundle.shared.ui.screen.compose)
        with(component) {
            alias(koin)
            alias(coil)
        }
    }
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            with(projects) {
                with(core) {
                    api(domain.interactor)
                    implementation(util)
                    implementation(presentation.feature)
                    implementation(presentation.theme)
                    implementation(presentation.uikit)
                    implementation(presentation.common)
                }
                implementation(feature.leaderboard.domain)
                implementation(navigation.api)
            }
            with(libs) {
                implementation(flagKit)
            }
        }
    }
}
