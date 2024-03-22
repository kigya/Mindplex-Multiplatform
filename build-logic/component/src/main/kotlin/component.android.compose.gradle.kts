plugins {
    id("org.jetbrains.compose")
    id("build-feature.compose")
}

dependencies {
    with(compose) {
        implementation(ui)
        implementation(material3)
    }
}
