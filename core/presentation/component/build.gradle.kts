plugins {
    with(libs.plugins.convention) {
        alias(config.shared.library)
        alias(bundle.shared.ui.screen.compose)
    }
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            with(projects) {
                implementation(core.presentation.theme)
                implementation(core.util)
                implementation(core.presentation.common)
            }
            with(libs) {
                implementation(compottie)
            }
        }
    }
}
