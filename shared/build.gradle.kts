plugins {
    with(libs.plugins.convention) {
        alias(config.shared.library)
        alias(bundle.shared.ui.screen.compose)
        alias(component.koin)
    }
}

kotlin.sourceSets {
    commonMain {
        dependencies {
            with(libs) {
                implementation(compose.navigation)
            }
            with(projects) {
                implementation(core.presentation.theme)
                implementation(navigation.navigator)
                implementation(feature.splash.presentation)
                implementation(feature.onboarding.presentation)
                implementation(feature.home.presentation)
                implementation(di.provider)
            }
        }
    }
}
