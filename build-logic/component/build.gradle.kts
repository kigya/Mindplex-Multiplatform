plugins {
    `kotlin-dsl`
}

dependencies {
    with(libs) {
        with(gradle) {
            implementation(kotlin)
            implementation(ksp)
            implementation(android)
            implementation(ktorfit)
            implementation(detekt)
        }
    }

    with(projects) {
        implementation(buildFeature)
        implementation(gradleExt)
    }
}
