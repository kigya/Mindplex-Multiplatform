plugins {
    with(libs.plugins.convention) {
        alias(config.shared.library)
    }
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            with(projects) {
                implementation(core.util)
                implementation(core.domain.interactor)
                implementation(core.domain.connectivity)
                implementation(core.domain.profile)
                implementation(feature.login.domain)
            }
        }
    }
}
