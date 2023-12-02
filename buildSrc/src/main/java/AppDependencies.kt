import Versions.lifecycleViewModelVersion
import Versions.livedataVersion
import org.gradle.api.artifacts.dsl.DependencyHandler

object AppDependencies {

    private const val buildToolsClassPath = "com.android.tools.build:gradle:${Versions.gradle}"
    private const val gradlePluginClassPath = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
    private const val navigationSafeArgsClassPath =
        "androidx.navigation:navigation-safe-args-gradle-plugin:${Versions.navigation}"
    private const val hiltAndroidClassPath =
        "com.google.dagger:hilt-android-gradle-plugin:${Versions.hiltAndroidGradle}"

    // Std lib
    private const val kotlinStdLib = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}"

    // Core
    private const val appcompat = "androidx.appcompat:appcompat:${Versions.appcompat}"
    private const val coreKtx = "androidx.core:core-ktx:${Versions.coreKtx}"

    // Glide
    const val glide = "com.github.bumptech.glide:glide:${Versions.glide}"
    const val glideCompiler = "com.github.bumptech.glide:compiler:${Versions.glide}"

    // Hilt
    const val hiltAndroid = "com.google.dagger:hilt-android:${Versions.hilt}"
    const val hiltCompiler = "com.google.dagger:hilt-android-compiler:${Versions.hilt}"

    // Lifecycle
    const val lifecycleExtension = "androidx.lifecycle:lifecycle-extensions:${Versions.lifecycleExtensionVersion}"
    const val lifecycleLivedataKtx = "androidx.lifecycle:lifecycle-livedata-ktx:$livedataVersion"
    const val lifecycleCompiler = "androidx.lifecycle:lifecycle-compiler:$livedataVersion"
    const val lifecycleViewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleViewModelVersion"

    //Coroutines
    const val coroutinesCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutinesVersions}"
    const val coroutinesAndroid =
        "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutinesVersions}"

    // Navigation
    private const val navigationFragment =
        "androidx.navigation:navigation-fragment-ktx:${Versions.navigation}"
    private const val navigationUi = "androidx.navigation:navigation-ui-ktx:${Versions.navigation}"


    // Network
    const val retrofit = "com.squareup.retrofit2:retrofit:2.9.0"
    private const val retrofitConverter = "com.squareup.retrofit2:converter-gson:2.9.0"
    const val gson = "com.google.code.gson:gson:2.8.9"
    private const val okhttp = "com.squareup.okhttp3:okhttp:4.9.2"
    private const val okhttpInterceptor = "com.squareup.okhttp3:logging-interceptor:4.9.2"

    // UI
    private const val constraintLayout =
        "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"
    private const val material = "com.google.android.material:material:${Versions.material}"
    const val cardView = "androidx.cardview:cardview:${Versions.cardView}"
    const val androidKtxActivity = "androidx.activity:activity-ktx:${Versions.activityKtxVersion}"
    const val androidKtxFragment = "androidx.fragment:fragment-ktx:${Versions.fragmentKtxVersion}"
    const val androidRecyclerView = "androidx.recyclerview:recyclerview:${Versions.recyclerVersion}"

    // Unit test
    private const val junit = "junit:junit:${Versions.junit}"
    private const val mockk = "io.mockk:mockk:${Versions.mockk}"
    private const val coreTesting = "androidx.arch.core:core-testing:${Versions.coreTesting}"
    private const val coroutinesTesting =
        "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.coroutinesTesting}"
    private const val turbine = "app.cash.turbine:turbine:${Versions.turbineVersion}"

    val buildClassPath = arrayListOf<String>().apply {
        add(buildToolsClassPath)
        add(gradlePluginClassPath)
        add(navigationSafeArgsClassPath)
        add(hiltAndroidClassPath)
    }


    val coreLibraries = arrayListOf<String>().apply {
        add(kotlinStdLib)
        add(coreKtx)
        add(coroutinesAndroid)
        add(coroutinesCore)
        add(appcompat)
    }


    val lifecycleLibraries = arrayListOf<String>().apply {
        add(lifecycleLivedataKtx)
        add(lifecycleExtension)
        add(lifecycleViewModel)
    }

    val navigationLibraries = arrayListOf<String>().apply {
        add(navigationFragment)
        add(navigationUi)
    }

    val networkLibraries = arrayListOf<String>().apply {
        add(retrofit)
        add(retrofitConverter)
        add(gson)
        add(okhttp)
        add(okhttpInterceptor)
    }

    val uiLibraries = arrayListOf<String>().apply {
        add(constraintLayout)
        add(material)
        add(androidKtxActivity)
        add(androidKtxFragment)
        add(cardView)
        add(androidRecyclerView)
    }

    val unitTestLibraries = arrayListOf<String>().apply {
        add(junit)
        add(mockk)
        add(coreTesting)
        add(coroutinesTesting)
        add(turbine)
    }
}

//util functions for adding the different type dependencies from build.gradle file
fun DependencyHandler.addClassPath(list: List<String>) {
    list.forEach { dependency ->
        add("classpath", dependency)
    }
}

fun DependencyHandler.ksp(list: List<String>) {
    list.forEach { dependency ->
        add("ksp", dependency)
    }
}

fun DependencyHandler.implementation(list: List<String>) {
    list.forEach { dependency ->
        add("implementation", dependency)
    }
}

fun DependencyHandler.androidTestImplementation(list: List<String>) {
    list.forEach { dependency ->
        add("androidTestImplementation", dependency)
    }
}

fun DependencyHandler.testImplementation(list: List<String>) {
    list.forEach { dependency ->
        add("testImplementation", dependency)
    }
}