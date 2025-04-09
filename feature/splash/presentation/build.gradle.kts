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
                    implementation(core.util)
                    implementation(core.presentation.common)
                    implementation(presentation.feature)
                    implementation(presentation.uikit)
                    implementation(presentation.theme)
                }
                with(feature) {
                    implementation(profile.domain)
                    implementation(onboarding.domain)
                    implementation(login.domain)
                }
                implementation(navigation.api)
            }
        }
    }
}
