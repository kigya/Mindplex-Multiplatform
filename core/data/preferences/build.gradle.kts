plugins {
    with(libs.plugins.convention) {
        alias(config.shared.library)
        alias(bundle.shared.ui.screen.compose)
    }
}

kotlin.sourceSets.commonMain {
    dependencies {
        api(libs.bundles.dataStore)
    }
}
