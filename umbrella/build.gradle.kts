plugins {
    with(libs.plugins.convention) {
        alias(config.shared.library)
    }
}

kotlin {
    sourceSets {
        commonMain {
            dependencies {
                with(projects) {
                    api(shared.core.presentation.theme)
                    api(shared.core.presentation.component)
                    api(shared.feature.onboarding.presentation)
                    // api(shared.core.util)
                }
            }
        }
    }
}
