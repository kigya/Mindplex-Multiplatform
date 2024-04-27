plugins {
    with(libs.plugins.convention) {
        alias(config.shared.library)
        alias(bundle.shared.ui.screen.compose)
        with(component) {
            alias(shared.decompose)
            alias(koin)
        }
    }
}

kotlin.sourceSets.commonMain {
    dependencies {
        with(projects) {
            implementation(di.core)
            implementation(core.util)
            implementation(core.presentation.resources)
            implementation(core.presentation.theme)
            implementation(core.presentation.component)
            api(core.presentation.feature)
            api(core.domain.interactor)
        }
    }
}
