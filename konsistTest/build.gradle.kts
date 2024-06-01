plugins {
    with(libs.plugins.convention) {
        alias(config.shared.library)
        alias(bundle.shared.ui.screen.compose)
        alias(component.jvm.konsist)
    }
}

tasks.withType<Test>(Test::useJUnitPlatform)

dependencies {
    with(projects) {
        testImplementation(core.presentation.feature)
        testImplementation(core.domain.interactor)
    }
}
