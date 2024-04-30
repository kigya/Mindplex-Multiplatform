plugins {
    id("org.jetbrains.compose")
    id("build-feature.compose")
}

dependencies {
    with(compose) {
        implementation(ui)
        implementation(material3)
    }

    with(libs) {
        with(detekt.plugins) {
            detektPlugins(compose)
        }
    }
}
