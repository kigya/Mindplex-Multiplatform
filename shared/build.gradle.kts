plugins {
    with(libs.plugins) {
        with(convention) {
            alias(config.shared.library)
        }
    }
}
