@file:Suppress("UnstableApiUsage")

plugins {
    id("com.android.library")
    kotlin("android")
}

android {
    compileSdk = AppConfig.compileSdk

    defaultConfig {
        minSdk = AppConfig.minSdk

        testInstrumentationRunner = AppConfig.androidTestInstrumentation
        consumerProguardFiles(AppConfig.proguardConsumerRules)
    }

    sourceSets {
        getByName("main") {
            assets.srcDirs("src/main/assets")
        }
    }

    buildTypes {
        getByName(AppConfig.release) {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile(AppConfig.proguardOptimize),
                AppConfig.proguardRules
            )
        }
    }

    buildFeatures {
        dataBinding = true
        viewBinding = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_18
        targetCompatibility = JavaVersion.VERSION_18
    }
    kotlinOptions {
        jvmTarget = "18"
    }
    viewBinding {
        android.buildFeatures.viewBinding = true
    }
}

dependencies {
    implementation(AppDependencies.uiLibraries)
    implementation(AppDependencies.coreLibraries)
    testImplementation(AppDependencies.unitTestLibraries)
}