plugins {
    with(libs.plugins.convention) {
        alias(config.shared.library)
        alias(component.unit.test)
        with(component) {
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
                implementation(kotlinx.datetime)
                implementation(firebase.firestore)
            }
            with(projects) {
                implementation(core.data.profile)
                implementation(feature.leaderboard.domain)
                implementation(core.util)
                implementation(core.data.firebase)
            }
        }
    }
}
