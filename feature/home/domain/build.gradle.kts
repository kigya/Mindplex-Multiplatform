plugins {
    with(libs.plugins.convention) {
        alias(config.shared.library)
    }
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            with(libs) {
                implementation(kotlinx.datetime)
            }
            with(projects) {
                implementation(feature.login.domain)
                implementation(core.util)
            }
        }
    }
}

