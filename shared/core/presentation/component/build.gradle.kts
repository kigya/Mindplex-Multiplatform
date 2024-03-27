plugins {
    with(libs.plugins.convention) {
        alias(config.shared.library)
        alias(bundle.shared.ui.screen.compose)
    }
}

kotlin.sourceSets.commonMain {
    dependencies {
        with(projects) {
            implementation(shared.core.presentation.theme)
        }
    }
}
