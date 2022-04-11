object Deps {

    const val androidGradlePlugin = "com.android.tools.build:gradle:7.1.2"


    object AndroidX {
        const val multidex = "androidx.multidex:multidex:2.0.1"
        const val annotation = "androidx.annotation:annotation:1.3.0"
        const val paging = "androidx.paging:paging-runtime-ktx:3.1.1"
        const val dataStore = "androidx.datastore:datastore:1.0.0"

        const val coreKtx = "androidx.core:core-ktx:1.7.0"
        const val fragment = "androidx.fragment:fragment-ktx:1.4.1"
        const val activity = "androidx.activity:activity-compose:1.4.0"

        const val appcompat = "androidx.appcompat:appcompat:1.4.1"
        const val material = "com.google.android.material:material:1.5.0"
        const val constraintLayout = "androidx.constraintlayout:constraintlayout:2.1.3"

        const val lifecycleRuntime = "androidx.lifecycle:lifecycle-runtime-ktx:2.4.1"
        const val lifecycleExtensions = "androidx.lifecycle:lifecycle-extensions:2.2.0"
        const val lifecycleViewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:2.4.1"

        const val viewpager2 = "androidx.viewpager2:viewpager2:1.0.0"
        const val recyclerview = "androidx.recyclerview:recyclerview:1.2.0"
    }

    object Kotlin {
        const val version = "1.6.10"
        const val stdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:$version"
        const val gradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$version"
        const val serialization = "org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.2"
    }

    object Coroutines {
        private const val version = "1.6.0"
        const val core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$version"
        const val android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$version"
        const val test = "org.jetbrains.kotlinx:kotlinx-coroutines-test:$version"

        const val concurrentFutures = "androidx.concurrent:concurrent-futures-ktx:1.1.0"
    }

    object Retrofit {
        const val okHttpVersion = "4.9.3"
        const val retrofit = "com.squareup.retrofit2:retrofit:2.9.0"
        const val okHttp = "com.squareup.okhttp3:okhttp:$okHttpVersion"
        const val loggingInterceptor = "com.squareup.okhttp3:logging-interceptor:$okHttpVersion"
    }

    object Hilt {
        const val hiltVersion = "2.41"
        const val hiltAndroid = "com.google.dagger:hilt-android:$hiltVersion"
        const val hiltCompiler = "com.google.dagger:hilt-compiler:$hiltVersion"
    }

    object Room {
        const val roomVersion = "2.4.2"
        const val roomKtx = "androidx.room:room-ktx:$roomVersion"
        const val roomRuntime = "androidx.room:room-runtime:$roomVersion"
        const val roomCompiler = "androidx.room:room-compiler:$roomVersion"
        const val roomPaging = "androidx.room:room-paging:$roomVersion"
    }

    object Squareup {
        const val leakcanary = "com.squareup.leakcanary:leakcanary-android:2.8.1"
    }

    object Pluto {
        private const val plutoVersion = "2.0.0"
        const val networkPluginDebug = "com.plutolib.plugins:network:$plutoVersion"
        const val exceptionsPluginDebug = "com.plutolib.plugins:exceptions:$plutoVersion"
        const val preferencesPluginDebug = "com.plutolib.plugins:preferences:$plutoVersion"
        const val plutoDebug = "com.plutolib:pluto:$plutoVersion"
    }

    object GSM {

        const val mapsKtx = "com.google.maps.android:maps-ktx:3.4.0"
        const val mapsUtilsKtx = "com.google.maps.android:maps-utils-ktx:3.4.0"

        const val location = "com.google.android.gms:play-services-location:19.0.1"
        const val maps = "com.google.android.gms:play-services-maps:18.0.2"
        const val utils = "com.google.maps.android:android-maps-utils:2.3.0"
    }

    object Jakewharton {
        const val timber = "com.jakewharton.timber:timber:5.0.1"
        const val adapter = "com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:0.8.0"
    }

    object Coil {
        private const val version = "1.4.0"
        const val coilCore = "io.coil-kt:coil:$version"
        const val coilCompose = "io.coil-kt:coil-compose:$version"
        const val coilGif = "io.coil-kt:coil-gif:$version"
        const val coilVideo = "io.coil-kt:coil-video:$version"
    }

    object Terrakok {
        const val cicerone = "com.github.terrakok:cicerone:7.1"
    }

    object Orbit {
        private const val version = "4.3.2"
        const val core = "org.orbit-mvi:orbit-core:$version"
        const val viewmodel = "org.orbit-mvi:orbit-viewmodel:$version"
        const val compose = "org.orbit-mvi:orbit-compose:$version"
        const val test = "org.orbit-mvi:orbit-test:$version"
    }

    object Other {
        const val circleImageView = "de.hdodenhof:circleimageview:3.1.0"
        const val viewBindingPropertyDelegate = "com.github.kirich1409:viewbindingpropertydelegate-noreflection:1.5.6"
    }


    object Test {
        const val junit = "junit:junit:4.13.2"
        const val androidxTestRunner = "androidx.test:runner:1.3.0"
        const val androidxTestEspresso = "androidx.test.espresso:espresso-core:3.3.0"
    }
}