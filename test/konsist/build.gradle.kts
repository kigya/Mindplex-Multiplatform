plugins {
    with(libs.plugins.convention) {
        alias(config.shared.library)
        alias(bundle.shared.ui.screen.compose)
    }
}

tasks.withType<Test>(Test::useJUnitPlatform)

dependencies {
    with(libs) {
        implementation(bundles.konsist)
    }
    with(projects) {
        testImplementation(core.presentation.feature)
        testImplementation(core.domain.interactor)
    }
}
