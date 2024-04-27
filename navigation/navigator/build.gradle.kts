plugins {
    with(libs.plugins.convention) {
        alias(config.shared.library)
        alias(component.shared.decompose)
    }
}
