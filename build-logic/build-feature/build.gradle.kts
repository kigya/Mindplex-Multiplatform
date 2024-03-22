plugins {
    `kotlin-dsl`
}

dependencies {
    with(libs) {
        with(gradle) {
            implementation(android)
        }
    }

    with(projects) {
        implementation(gradleExt)
    }
}
