// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.google.devtools.ksp") version "1.8.10-1.0.9" apply false
}
buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        addClassPath(AppDependencies.buildClassPath)
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.8.0")
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}