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
            with(libs) {
                implementation(compose.navigation)
                implementation(kmpAuth.google)
            }
            with(projects) {
                with(feature) {
                    implementation(splash.presentation)
                    implementation(onboarding.presentation)
                    implementation(login.presentation)
                    implementation(home.presentation)
                    implementation(leaderboard.presentation)
                    implementation(profile.presentation)
                }
                with(core) {
                    implementation(presentation.theme)
                    implementation(presentation.feature)
                    implementation(presentation.common)
                }
                implementation(navigation.api)
                implementation(di.api)
            }
        }
    }
}
