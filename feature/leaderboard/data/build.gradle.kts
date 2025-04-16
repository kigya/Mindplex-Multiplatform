plugins {
    with(libs.plugins.convention) {
        alias(config.shared.library)
        with(component) {
            alias(unit.test)
            alias(room)
            alias(serialization)
        }
    }
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            with(libs) {
                implementation(coroutines.core)
                implementation(firebase.firestore)
            }
            with(projects) {
                implementation(feature.leaderboard.domain)
                with(core) {
                    implementation(data.profile)
                    implementation(util)
                    implementation(data.firebase)
                }
            }
        }
    }
}
