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
                    api(domain.interactor)
                    implementation(util)
                    implementation(domain.profile)
                    implementation(presentation.common)
                    implementation(presentation.feature)
                    implementation(presentation.uikit)
                    implementation(presentation.theme)
                }
                implementation(feature.profile.domain)
                implementation(navigation.api)
            }
        }
    }
}
