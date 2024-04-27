plugins {
    with(libs.plugins.convention) {
        alias(config.shared.library)
        alias(bundle.shared.ui.screen.compose)
        with(component) {
            alias(shared.decompose)
            alias(koin)
        }
    }
}

kotlin.sourceSets.commonMain {
    dependencies {
        with(projects) {
            with(core) {
                api(presentation.feature)
                implementation(util)
                implementation(presentation.resources)
                implementation(presentation.theme)
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
