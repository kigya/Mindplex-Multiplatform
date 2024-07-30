plugins {
    with(libs.plugins.convention) {
        alias(config.shared.library)
        with(component) {
            alias(koin)
            alias(coil)
            alias(room)
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
                implementation(jwt.parser)
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
                    implementation(home.presentation)
                }
                with(core) {
                    implementation(data.connectivity)
                    implementation(data.profile)
                    implementation(domain.connectivity)
                    implementation(domain.profile)
                    implementation(presentation.feature)
                }
                with(navigation) {
                    implementation(internal)
                    implementation(api)
                }
            }
        }
    }
}
