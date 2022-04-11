plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
    id("kotlin-parcelize")
    kotlin("plugin.serialization")
    id("dagger.hilt.android.plugin")
}

android {
    compileSdk = Config.compileSdkVersion
    buildToolsVersion = Config.buildToolsVersion

    defaultConfig {
        minSdk = Config.minSdkVersion
        targetSdk = Config.targetSdkVersion
//        versionCode = Config.versionCode
//        versionName = Config.versionName
        consumerProguardFiles(Config.consumerProguardFiles)
        testInstrumentationRunner = Config.testInstrumentationRunner
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_11.toString()
        freeCompilerArgs  = (freeCompilerArgs + Config.freeCompilerArgs).distinct()
    }

    buildFeatures {
        viewBinding = true
    }
}


dependencies {
    implementation(Deps.Hilt.hiltAndroid)
    kapt(Deps.Hilt.hiltCompiler)

    testImplementation(Deps.Test.junit)
    androidTestImplementation(Deps.Test.androidxTestRunner)
    androidTestImplementation(Deps.Test.androidxTestEspresso)
}