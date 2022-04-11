import org.jetbrains.kotlin.gradle.dsl.KotlinJvmCompile
import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    kotlin("plugin.serialization")
    id("dagger.hilt.android.plugin")
    id("com.google.gms.google-services")
    id("com.google.devtools.ksp") version "1.6.10-1.0.4"
}

android {
    val properties = gradleLocalProperties(rootDir)

    compileSdk = Config.compileSdkVersion
    buildToolsVersion = Config.buildToolsVersion

    defaultConfig {
        applicationId = Config.applicationId
        minSdk = Config.minSdkVersion
        targetSdk = Config.targetSdkVersion
        versionCode = Config.versionCode
        versionName = Config.versionName
        multiDexEnabled = Config.multiDexEnabled
        testInstrumentationRunner = Config.testInstrumentationRunner

        manifestPlaceholders["map_api_key"] = properties.getProperty("map_api_key")
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "11"
    }

    buildFeatures {
        viewBinding = true
    }
}

kapt {
    correctErrorTypes = true
}

tasks.withType<KotlinJvmCompile> {
    kotlinOptions {
        freeCompilerArgs = (freeCompilerArgs + Config.freeCompilerArgs).distinct()
    }
}

hilt {
    enableExperimentalClasspathAggregation = true
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(Deps.Kotlin.stdlib)
    implementation(Deps.AndroidX.multidex)


    implementation(Deps.AndroidX.coreKtx)
    implementation(Deps.AndroidX.activity)
    implementation(Deps.AndroidX.fragment)

    implementation(Deps.AndroidX.material)
    implementation(Deps.AndroidX.appcompat)
    implementation(Deps.AndroidX.constraintLayout)

    implementation(Deps.AndroidX.lifecycleRuntime)
    implementation(Deps.AndroidX.lifecycleExtensions)
    implementation(Deps.AndroidX.lifecycleViewModel)

    implementation(Deps.Orbit.core)
    implementation(Deps.Orbit.compose)
    implementation(Deps.Orbit.viewmodel)

    implementation(Deps.GSM.utils)
    implementation(Deps.GSM.maps)
    implementation(Deps.GSM.mapsKtx)
    implementation(Deps.GSM.location)
    implementation(Deps.GSM.mapsUtilsKtx)

    implementation(Deps.AndroidX.viewpager2)
    implementation(Deps.AndroidX.recyclerview)

    implementation(Deps.Hilt.hiltAndroid)
    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.4.1")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.4.1")
    kapt(Deps.Hilt.hiltCompiler)

    implementation(Deps.Coroutines.core)
    implementation(Deps.Coroutines.android)

    implementation(Deps.Kotlin.serialization)
    implementation(Deps.AndroidX.annotation)

    implementation(Deps.Jakewharton.timber)
    implementation(Deps.Jakewharton.adapter)

    implementation(Deps.Coil.coilCore)
    implementation(Deps.Other.viewBindingPropertyDelegate)

    implementation(Deps.Terrakok.cicerone)


    // Logging interceptor: Chucker
    debugImplementation(Deps.Pluto.plutoDebug)
    debugImplementation(Deps.Pluto.exceptionsPluginDebug)
    debugImplementation(Deps.Pluto.networkPluginDebug)
    debugImplementation(Deps.Pluto.preferencesPluginDebug)

    implementation(Deps.Retrofit.okHttp)
    implementation(Deps.Retrofit.retrofit)
    implementation(Deps.Retrofit.loggingInterceptor)

    implementation(Deps.Room.roomKtx)
    implementation(Deps.Room.roomPaging)
    implementation(Deps.AndroidX.paging)
    implementation(Deps.Room.roomRuntime)
    ksp(Deps.Room.roomCompiler)

    implementation(Deps.AndroidX.dataStore)


    testImplementation(Deps.Test.junit)
    androidTestImplementation(Deps.Test.androidxTestRunner)
    androidTestImplementation(Deps.Test.androidxTestEspresso)
}