plugins {
    with(libs.plugins.convention) {
        alias(config.shared.library)
        alias(bundle.shared.ui.screen.compose)
        alias(component.shared.decompose)
    }
}

kotlin.sourceSets.commonMain {
    dependencies {
        with(projects) {
            implementation(core.util)
            implementation(core.domain.interactor)
        }
    }
}
