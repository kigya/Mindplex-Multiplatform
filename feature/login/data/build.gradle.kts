plugins {
    with(libs.plugins.convention) {
        alias(config.shared.library)
        alias(component.serialization)
        alias(component.unit.test)
    }
}

kotlin {
    sourceSets {
        commonMain {
            dependencies {
                with(libs) {
                    implementation(coroutines.core)
                    implementation(bundles.dataStore)
                    implementation(firebase.firestore)
                    implementation(firebase.common)
                    implementation(ktor.serialization)
                    implementation(ktor.client.logging)
                    implementation(ktor.client.content.negotiation)
                }
                with(projects) {
                    implementation(feature.login.domain)
                    implementation(core.util)
                    implementation(core.data.firebase)
                    implementation(core.data.jwtParser)
                }
            }
        }
        androidMain {
            dependencies {
                implementation(libs.ktor.client.android)
            }
        }
        iosMain {
            dependencies {
                implementation(libs.ktor.client.darwin)
            }
        }
    }
}

