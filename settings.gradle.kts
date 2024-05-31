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
    ":core:data:preferences",
    ":core:domain:interactor",
    ":core:presentation:resources",
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

    // Navigation Modules
    ":navigation:mediator",
    ":navigation:navigator",

    // DI Modules
    ":di:provider",
    ":di:core",

    // Application Modules
    ":androidApp",
    ":shared",

    // Test Modules
    ":konsistTest",
)