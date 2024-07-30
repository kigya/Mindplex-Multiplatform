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
                implementation(compose.navigation)
                implementation(coroutines.core)
            }
            with(projects) {
                implementation(core.util)
                implementation(core.presentation.common)
                implementation(core.presentation.component)
                implementation(core.domain.interactor)
                implementation(navigation.api)
            }
        }
    }
}
