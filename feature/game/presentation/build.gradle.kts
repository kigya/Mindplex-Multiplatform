plugins {
    with(libs.plugins.convention) {
        alias(config.shared.library)
        alias(bundle.shared.ui.screen.compose)
    }
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            with(libs) {
                implementation(compottie)
            }
            with(projects) {
                with(core) {
                    api(domain.interactor)
                    implementation(core.util)
                    implementation(core.presentation.common)
                    implementation(presentation.feature)
                    implementation(presentation.uikit)
                    implementation(presentation.theme)
                }
                implementation(feature.game.domain)
                implementation(navigation.api)
            }
        }
    }
}
