plugins {
    with(libs.plugins.convention) {
        alias(config.shared.library)
        with(component) {
            alias(shared.decompose)
            alias(koin)
        }
    }
}

kotlin.sourceSets.commonMain {
    dependencies {
        with(projects) {
            with(feature) {
                implementation(onboarding.presentation)
                implementation(splash.presentation)
                implementation(home.presentation)
            }
            with(di) {
                implementation(core)
                implementation(provider)
            }
            implementation(navigation.navigator)
        }
    }
}
