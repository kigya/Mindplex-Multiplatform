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
                implementation(compottie)
            }
            with(projects) {
                with(core) {
                    implementation(util)
                    implementation(presentation.feature)
                    implementation(presentation.theme)
                    implementation(presentation.uikit)
                    implementation(presentation.common)
                }
                implementation(feature.onboarding.domain)
                implementation(navigation.api)
            }
        }
    }
}
