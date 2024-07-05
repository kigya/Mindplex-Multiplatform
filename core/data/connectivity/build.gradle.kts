plugins {
    with(libs.plugins.convention) {
        alias(config.shared.library)
    }
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            with(libs) {
                implementation(coroutines.core)
                implementation(connectivity.core)
                implementation(connectivity.device)
            }
            with(projects) {
                implementation(core.domain.connectivity)
            }
        }
    }
}
