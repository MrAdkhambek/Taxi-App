plugins {
    `kotlin-dsl`
}

repositories {
    google()
    mavenCentral()
}

dependencies {
    implementation(Deps.androidGradlePlugin)
    implementation(Deps.Kotlin.gradlePlugin)

    implementation(kotlin("serialization", version = Deps.Kotlin.version))
    implementation("com.google.dagger", "hilt-android-gradle-plugin", Deps.Hilt.hiltVersion)
}

kotlin {
    sourceSets.getByName("main").kotlin.srcDir("buildSrc/src/main/kotlin")
}