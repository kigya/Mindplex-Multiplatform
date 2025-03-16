plugins {
    with(libs.plugins.convention) {
        alias(config.shared.library)
        alias(bundle.shared.ui.screen.compose)
        alias(component.koin)
    }
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            with(libs) {
                implementation(kmpAuth.google)
            }
            with(projects) {
                with(core) {
                    api(domain.interactor)
                    implementation(core.util)
                    implementation(presentation.feature)
                    implementation(presentation.theme)
                    implementation(presentation.uikit)
                    implementation(presentation.common)
                }
                implementation(feature.login.domain)
                implementation(navigation.api)
            }
        }
    }
}
