plugins {
    with(libs.plugins.convention) {
        alias(config.shared.library)
    }
}

kotlin.sourceSets {
    commonMain {
        dependencies {
            with(libs) {
                implementation(coroutines.core)
                implementation(bundles.dataStore)
            }
            with(projects) {
                implementation(feature.onboarding.domain)
            }
        }
    }
}
