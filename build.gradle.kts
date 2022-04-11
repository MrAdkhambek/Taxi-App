buildscript {
    extra["kotlin_version"] = Deps.Kotlin.version

    repositories {
        google()
        mavenCentral()
    }

    dependencies {
        classpath(Deps.androidGradlePlugin)
        classpath(Deps.Kotlin.gradlePlugin)

        classpath(kotlin("serialization", version = Deps.Kotlin.version))
        classpath("com.google.dagger", "hilt-android-gradle-plugin", Deps.Hilt.hiltVersion)
        classpath("com.google.gms:google-services:4.3.10")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.6.20")
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
        maven("https://jitpack.io")
    }
}

tasks {
    val clean by registering(Delete::class) {
        delete(buildDir)
    }
}