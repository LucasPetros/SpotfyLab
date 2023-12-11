plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    id("com.google.gms.google-services")
    id("dagger.hilt.android.plugin")
    id("androidx.navigation.safeargs")
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
    id("com.google.firebase.crashlytics")

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

    hilt {
        enableAggregatingTask = true
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

secrets {
    propertiesFileName = "local.properties"
}

dependencies {
    implementation(project(Modules.commons))
    implementation(project(Modules.network))
    implementation(project(Modules.ui))
    implementation(project(Modules.analytics))

    implementation(AppDependencies.coreLibraries)
    implementation(AppDependencies.hiltAndroid)

    implementation(project.dependencies.platform(AppDependencies.firebasePlatform))
    implementation(AppDependencies.firebaseAnalytics)
    implementation(AppDependencies.fireBaseCrashlytics)

    kapt(AppDependencies.hiltCompiler)
    kapt(AppDependencies.roomCompiler)
    implementation(AppDependencies.lifecycleLibraries)
    implementation(AppDependencies.navigationLibraries)
    implementation(AppDependencies.uiLibraries)

    implementation(AppDependencies.retrofit)
    implementation(AppDependencies.gson)
    implementation(AppDependencies.cardView)
    implementation(AppDependencies.roomLibraries)

    testImplementation(AppDependencies.unitTestLibraries)
    androidTestImplementation(AppDependencies.androidTestLibraries)
}
