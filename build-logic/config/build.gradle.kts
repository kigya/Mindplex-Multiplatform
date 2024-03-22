plugins {
    `kotlin-dsl`
}

dependencies {
    with(libs) {
        with(gradle) {
            implementation(kotlin)
            implementation(kotlin.multiplatform)
            implementation(android)
        }
    }

    with(projects) {
        implementation(gradleExt)
    }
}
