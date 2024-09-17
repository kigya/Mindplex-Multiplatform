plugins {
    with(libs.plugins.convention) {
        alias(config.shared.library)
        alias(component.unit.test)
    }
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            with(libs) {
                implementation(coroutines.core)
            }
            with(projects) {
                implementation(feature.leaderboard.domain)
            }
        }
    }
}
