plugins {
    id("com.android.application")
    kotlin("android")
    id("dagger.hilt.android.plugin")
    id("com.google.devtools.ksp")
    id("androidx.navigation.safeargs")
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")

}

android {
    compileSdk = AppConfig.compileSdk

    defaultConfig {
        applicationId = "com.lucas.petros.spotfylab"
        minSdk = AppConfig.minSdk
        targetSdk = AppConfig.targetSdk
        versionCode = AppConfig.versionCode
        versionName = AppConfig.versionName

        manifestPlaceholders["redirectSchemeName"] = "spotify-sdk"
        manifestPlaceholders["redirectHostName"] = "auth"

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

secrets {
    propertiesFileName = "local.properties"
}

dependencies {
    implementation(project(Modules.commons))
    implementation(project(Modules.network))
    implementation(project(Modules.ui))
    implementation("com.spotify.android:auth:2.1.0")
    implementation("androidx.browser:browser:1.4.0")

    implementation(AppDependencies.coreLibraries)
    implementation(AppDependencies.hiltAndroid)
    implementation(AppDependencies.kspLibrary)
    ksp(AppDependencies.hiltCompiler)
    implementation(AppDependencies.lifecycleLibraries)
    implementation(AppDependencies.navigationLibraries)
    implementation(AppDependencies.uiLibraries)

    implementation(AppDependencies.retrofit)
    implementation(AppDependencies.gson)
    implementation(AppDependencies.cardView)

    testImplementation(AppDependencies.unitTestLibraries)

}