import org.jetbrains.kotlin.kapt3.base.Kapt.kapt

plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
}

android {
    compileSdk = AppConfig.compileSdk

    defaultConfig {
        minSdk = AppConfig.minSdk

        testInstrumentationRunner = AppConfig.androidTestInstrumentation
        consumerProguardFiles(AppConfig.proguardConsumerRules)
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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_18
        targetCompatibility = JavaVersion.VERSION_18
    }
    kotlinOptions {
        jvmTarget = "18"
    }
    viewBinding {
        android.buildFeatures.dataBinding = true
    }
}

dependencies {
    implementation(AppDependencies.coreLibraries)
    implementation(AppDependencies.uiLibraries)
    implementation(AppDependencies.lifecycleLibraries)
    implementation(AppDependencies.hiltAndroid)
    implementation(project(mapOf("path" to ":analytics")))
    kapt(AppDependencies.hiltCompiler)
    kapt(AppDependencies.lifecycleCompiler)
    implementation(AppDependencies.glide)
    kapt(AppDependencies.glideCompiler)
}