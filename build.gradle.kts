plugins {
    with(libs.plugins) {
        with(android) {
            alias(application) apply false
            alias(library) apply false
        }
        with(kotlin) {
            alias(android) apply false
            alias(multiplatform) apply false
        }

        alias(ksp) apply false
        alias(ktorfit) apply false
        alias(compose) apply false

        alias(detekt) apply false
    }
}

apply {
    from("config/git/hooks/installer.gradle.kts")
}
