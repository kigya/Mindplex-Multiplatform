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
                    api(presentation.feature)
                    api(domain.interactor)
                    api(core.util)
                    implementation(presentation.theme)
                    implementation(presentation.component)
                }
                api(feature.login.domain)
                implementation(navigation.navigator)
            }
        }
    }
}
