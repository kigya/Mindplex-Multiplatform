plugins {
    with(libs.plugins.convention) {
        alias(config.shared.library)
        with(component) {
            alias(unit.test)
            alias(room)
            alias(serialization)
            alias(ktor)
        }
    }
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            with(libs) {
                implementation(coroutines.core)
                with(dataStore) {
                    implementation(core)
                    implementation(preferences)
                }
            }
            with(projects) {
                implementation(feature.leaderboard.domain)
                with(core) {
                    implementation(util)
                    with(data){
                        implementation(profile)
                        implementation(firebase)
                        implementation(scout)
                    }
                }
            }
        }
    }
}
