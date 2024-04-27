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
                api(domain.interactor)
                implementation(presentation.resources)
                implementation(presentation.component)
                api(core.util)
            }
            api(feature.onboarding.domain)
            implementation(navigation.navigator)
        }
        with(libs) {
            implementation(compottie)
        }
    }
}
