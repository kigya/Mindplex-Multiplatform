@file:Suppress("UnstableApiUsage")

rootProject.name = "Mindplex-Multiplatform"

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

includeBuild("build-logic")

// Core Modules
include(
    ":core:data:connectivity",
    ":core:data:firebase",
    ":core:domain:connectivity",
    ":core:domain:interactor",
    ":core:presentation:feature",
    ":core:presentation:component",
    ":core:presentation:theme",
    ":core:util",

    // Feature Modules
    ":feature:home:domain",
    ":feature:home:data",
    ":feature:home:presentation",
    ":feature:onboarding:domain",
    ":feature:onboarding:data",
    ":feature:onboarding:presentation",
    ":feature:splash:presentation",
    ":feature:login:domain",
    ":feature:login:data",
    ":feature:login:presentation",

    // Navigation Modules
    ":navigation:navigator",

    // DI Modules
    ":di:provider",

    // Application Modules
    ":androidApp",
    ":shared",

    // Test Modules
    ":konsistTest",
)
