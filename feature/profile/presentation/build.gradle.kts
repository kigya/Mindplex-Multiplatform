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
                    with(presentation){
                        implementation(feature)
                        implementation(theme)
                        implementation(uikit)
                        implementation(common)
                    }
                }
                implementation(feature.login.domain)
                implementation(feature.profile.domain)
                implementation(navigation.api)
            }
            implementation(libs.flagKit)
            implementation(libs.bundles.dataStore)
        }
    }
}
