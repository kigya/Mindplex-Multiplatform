plugins {
    with(libs.plugins.convention) {
        alias(config.shared.library)
        with(component) {
            alias(koin)
            alias(coil)
            alias(room)
            alias(ktor)
            alias(serialization)
        }
    }
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            with(libs) {
                implementation(bundles.dataStore)
                implementation(connectivity.core)
                implementation(connectivity.device)
            }
            with(projects) {
                with(feature) {
                    implementation(splash.presentation)
                    implementation(onboarding.data)
                    implementation(onboarding.domain)
                    implementation(onboarding.presentation)
                    implementation(login.data)
                    implementation(login.domain)
                    implementation(login.presentation)
                    implementation(home.data)
                    implementation(home.domain)
                    implementation(home.presentation)
                    implementation(profile.domain)
                    implementation(profile.presentation)
                    implementation(leaderboard.data)
                    implementation(leaderboard.domain)
                    implementation(leaderboard.presentation)
                    implementation(game.data)
                    implementation(game.domain)
                    implementation(game.presentation)
                }
                with(core) {
                    implementation(data.connectivity)
                    implementation(data.profile)
                    implementation(domain.connectivity)
                    implementation(domain.profile)
                    implementation(presentation.feature)
                    implementation(data.scout)
                    implementation(util)
                }
                with(navigation) {
                    implementation(internal)
                    implementation(api)
                }
            }
        }
    }
}
