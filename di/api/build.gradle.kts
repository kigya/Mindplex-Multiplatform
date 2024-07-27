plugins {
    with(libs.plugins.convention) {
        alias(config.shared.library)
        alias(component.koin)
    }
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            with(projects) {
                implementation(di.internal)
            }
        }
    }
}
