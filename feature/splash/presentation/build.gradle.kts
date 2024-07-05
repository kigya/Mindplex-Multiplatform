plugins {
    with(libs.plugins.convention) {
        alias(config.shared.library)
        alias(bundle.shared.ui.screen.compose)
        with(component) {
            alias(koin)
        }
    }
}

kotlin.sourceSets {
    commonMain {
        dependencies {
            with(projects) {
                with(core) {
                    api(presentation.feature)
                    api(domain.interactor)
                    api(core.util)
                    implementation(presentation.component)
                }
                api(feature.onboarding.domain)
                implementation(navigation.navigator)
            }
            with(libs) {
                implementation(compottie)
            }
        }
    }
}
