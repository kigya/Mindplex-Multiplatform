plugins {
    id("component.android.compose")
}

dependencies {
    with(compose) {
        implementation(preview)
        debugImplementation(uiTooling)
    }
}
