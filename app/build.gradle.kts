plugins {
    id("com.android.application")
    kotlin("android")
    id("dagger.hilt.android.plugin")
    id("com.google.devtools.ksp")
    id("androidx.navigation.safeargs")
}

android {
    compileSdk = AppConfig.compileSdk

    defaultConfig {
        applicationId = "com.lucas.petros.spotfylab"
        minSdk = AppConfig.minSdk
        targetSdk = AppConfig.targetSdk
        versionCode = AppConfig.versionCode
        versionName = AppConfig.versionName

        testInstrumentationRunner = AppConfig.customInstrumentedRunner
    }

    buildTypes {
        getByName(AppConfig.release) {
            isMinifyEnabled = true
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
        android.buildFeatures.viewBinding = true
        android.buildFeatures.dataBinding = true
    }

}

dependencies {
    implementation(project(Modules.commons))
    implementation(project(Modules.network))
    implementation(project(Modules.ui))

    implementation(AppDependencies.coreLibraries)
    implementation(AppDependencies.hiltAndroid)
    ksp(AppDependencies.hiltCompiler)
    implementation(AppDependencies.lifecycleLibraries)
    implementation(AppDependencies.navigationLibraries)
    implementation(AppDependencies.uiLibraries)

    implementation(AppDependencies.retrofit)
    implementation(AppDependencies.gson)
    implementation(AppDependencies.cardView)

    testImplementation(AppDependencies.unitTestLibraries)

}