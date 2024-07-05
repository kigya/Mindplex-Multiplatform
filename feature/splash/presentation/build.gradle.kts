plugins {
    with(libs.plugins.convention) {
        alias(config.shared.library)
        alias(bundle.shared.ui.screen.compose)
        with(component) {
            alias(koin)
        }
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
                    api(presentation.feature)
                    api(domain.interactor)
                    api(core.util)
                    implementation(presentation.component)
                }
                with(feature) {
                    implementation(onboarding.domain)
                    implementation(login.domain)
                }
                implementation(navigation.navigator)
            }
        }
    }
}
