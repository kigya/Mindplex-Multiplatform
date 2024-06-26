plugins {
    with(libs.plugins.convention) {
        alias(config.shared.library)
        alias(component.koin)
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
                    implementation(core.data.connectivity)
                    implementation(core.domain.connectivity)
                    implementation(home.presentation)
                }
                implementation(navigation.navigator)
            }
        }
    }
}
