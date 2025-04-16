plugins {
    with(libs.plugins.convention) {
        alias(config.shared.library)
        alias(bundle.shared.ui.screen.compose)
        with(component) {
            alias(koin)
            alias(coil)
        }
    }
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            with(projects) {
                with(core) {
                    api(domain.interactor)
                    implementation(util)
                    implementation(domain.profile)
                    with(presentation) {
                        implementation(feature)
                        implementation(theme)
                        implementation(uikit)
                        implementation(common)
                    }
                }
                with(feature) {
                    implementation(login.domain)
                    implementation(profile.domain)
                }
                implementation(navigation.api)
            }
            with(libs) {
                implementation(flagKit)
                implementation(bundles.dataStore)
            }
        }
    }
}
