// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("org.jetbrains.kotlin.android") version "1.8.10" apply false
}
buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        addClassPath(AppDependencies.buildClassPath)
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.8.0")
        classpath ("com.google.android.libraries.mapsplatform.secrets-gradle-plugin:secrets-gradle-plugin:2.0.1")
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}