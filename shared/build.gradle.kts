plugins {
    with(libs.plugins.convention) {
        alias(config.shared.library)
        alias(bundle.shared.ui.screen.compose)
        alias(component.shared.decompose)
        alias(component.koin)
    }
}

kotlin.sourceSets.commonMain {
    dependencies {
        with(projects) {
            implementation(core.presentation.theme)
            implementation(navigation.mediator)
            implementation(feature.splash.presentation)
            implementation(feature.onboarding.presentation)
            implementation(feature.home.presentation)
            implementation(di.provider)
        }
    }
}
