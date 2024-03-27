@file:Suppress("UnstableApiUsage")

include(
    ":androidApp",
    ":umbrella",
    ":shared:core:util",
    ":shared:core:presentation:theme",
    ":shared:core:presentation:component",
    ":shared:feature:onboarding:presentation"
)

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Mindplex-Multiplatform"
includeBuild("build-logic")
