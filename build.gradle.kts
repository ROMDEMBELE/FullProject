plugins {
    // this is necessary to avoid the plugins to be loaded multiple times
    // in each subproject's classloader
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.androidLibrary) apply false
    alias(libs.plugins.jetbrainsCompose) apply false
    alias(libs.plugins.compose.compiler) apply false
    alias(libs.plugins.kotlinMultiplatform) apply false
    alias(libs.plugins.sqlDelight) apply false
    alias(libs.plugins.realm) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.room) apply false
    kotlin("plugin.serialization") version "2.0.0-RC3" apply false
}