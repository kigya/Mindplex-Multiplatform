plugins {
    with(libs.plugins.convention) {
        alias(config.shared.library)
        alias(bundle.shared.ui.screen.compose)
        with(component) {
            alias(koin)
            alias(coil)
        }
    }
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            with(projects) {
                implementation(feature.home.domain)
                implementation(navigation.api)
                with(core) {
                    api(domain.interactor)
                    implementation(util)
                    implementation(presentation.feature)
                    implementation(presentation.theme)
                    implementation(presentation.uikit)
                    implementation(presentation.common)
                    implementation(domain.profile)
                }
            }
        }
    }
}
