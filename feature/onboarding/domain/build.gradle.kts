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
            }
            with(projects) {
                api(core.domain.interactor)
            }
        }
    }
}
