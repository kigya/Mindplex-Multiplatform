plugins {
    with(libs.plugins.convention) {
        alias(config.shared.library)
        alias(bundle.shared.ui.screen.compose)
        alias(component.koin)
    }
}

kotlin.sourceSets {
    with(libs) {
        commonMain {
            dependencies {
                implementation(coroutines.core)
                implementation(compose.navigation)
            }
        }
    }
}
