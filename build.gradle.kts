// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("org.jetbrains.kotlin.android") version "1.8.10" apply false
    id("com.android.library") version "7.4.1" apply false
    id("com.google.gms.google-services") version "4.3.15" apply false
}
buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        addClassPath(AppDependencies.buildClassPath)
        classpath("com.google.firebase:firebase-crashlytics-gradle:2.9.9")
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}