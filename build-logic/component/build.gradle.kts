plugins {
    `kotlin-dsl`
}

dependencies {
    with(libs) {
        with(gradle) {
            implementation(kotlin)
            implementation(kotlinx.serialization)
            implementation(ksp)
            implementation(android)
            implementation(ktorfit)
            implementation(koin)
        }
    }

    with(projects) {
        implementation(buildFeature)
        implementation(gradleExt)
    }
}
