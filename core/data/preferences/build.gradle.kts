plugins {
    with(libs.plugins.convention) {
        alias(config.shared.library)
        alias(component.shared.test)
    }
}

kotlin.sourceSets.commonMain {
    dependencies {
        api(libs.bundles.dataStore)
    }
}
