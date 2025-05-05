plugins {
    with(libs.plugins.convention) {
        alias(config.shared.library)
        with(component) {
            alias(serialization)
            alias(ktor)
            alias(unit.test)
        }
    }
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            with(libs) {
                implementation(coroutines.core)
                implementation(bundles.dataStore)
            }
            with(projects) {
                implementation(feature.login.domain)
                with(core) {
                    implementation(util)
                    with(data){
                        implementation(firebase)
                        implementation(scout)
                    }
                }
            }
        }
    }
}
