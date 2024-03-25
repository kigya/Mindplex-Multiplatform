@file:Suppress("UnstableApiUsage")

include(":shared:core:presentation:component")


include(
    ":androidApp",
    ":shared:core:presentation:theme",
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
