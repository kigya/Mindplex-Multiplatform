plugins {
    with(libs.plugins.convention) {
        alias(config.shared.library)
        with(component) {
            alias(serialization)
        }
    }
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            with(projects) {
                implementation(core.domain.jwtParser)
                implementation(core.util)
            }
        }
    }
}
