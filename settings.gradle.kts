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

// Modules
include(

    // Core Modules
    ":core:data:connectivity",
    ":core:data:firebase",
    ":core:data:profile",
    ":core:data:jwt-parser",
    ":core:data:scout",
    ":core:domain:connectivity",
    ":core:domain:interactor",
    ":core:domain:profile",
    ":core:presentation:feature",
    ":core:presentation:uikit",
    ":core:presentation:theme",
    ":core:presentation:common",
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
    ":feature:leaderboard:domain",
    ":feature:leaderboard:data",
    ":feature:leaderboard:presentation",
    ":feature:profile:domain",
    ":feature:profile:data",
    ":feature:profile:presentation",
    ":feature:game:data",
    ":feature:game:domain",
    ":feature:game:presentation",

    // Navigation Modules
    ":navigation:internal",
    ":navigation:api",

    // DI Modules
    ":di:internal",
    ":di:api",

    // Application Modules
    ":androidApp",
    ":shared",

    // Test Modules
    ":test:konsist",
)
