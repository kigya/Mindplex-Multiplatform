plugins {
    with(libs.plugins.convention) {
        alias(config.shared.library)
        alias(component.shared.decompose)
        alias(component.koin)
    }
}

kotlin.sourceSets.commonMain {
    dependencies {
        with(libs) {
            implementation(bundles.dataStore)
        }
        with(projects) {
            with(feature) {
                implementation(splash.presentation)
                implementation(onboarding.data)
                implementation(onboarding.domain)
                implementation(onboarding.presentation)
            }
            implementation(navigation.navigator)
        }
    }
}
