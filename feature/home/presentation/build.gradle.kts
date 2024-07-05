plugins {
    with(libs.plugins.convention) {
        alias(config.shared.library)
        alias(bundle.shared.ui.screen.compose)
        with(component) {
            alias(koin)
        }
    }
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            with(projects) {
                with(core) {
                    implementation(util)
                    implementation(presentation.theme)
                    implementation(presentation.component)
                    api(presentation.feature)
                    api(domain.interactor)
                }
            }
        }
    }
}
