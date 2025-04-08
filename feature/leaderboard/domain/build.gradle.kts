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
                with(core) {
                    api(domain.interactor)
                    implementation(util)
                    implementation(domain.connectivity)
                }
            }
        }
    }
}
