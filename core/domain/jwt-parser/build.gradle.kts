plugins {
    with(libs.plugins.convention) {
        alias(config.shared.library)
    }
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            with(projects) {
                api(core.domain.interactor)
                implementation(core.util)
            }
        }
    }
}
