object Config {
    const val compileSdkVersion = 31
    const val buildToolsVersion = "31"

    const val applicationId = "me.adkhambek.taxi"
    const val minSdkVersion = 21
    const val targetSdkVersion = 31
    const val versionCode = 1
    const val versionName = "1.0"
    const val multiDexEnabled = true
    const val consumerProguardFiles = "consumer-rules.pro"
    const val testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

    val freeCompilerArgs = listOf(
        "-Xuse-experimental=kotlinx.serialization.ExperimentalSerializationApi",
        "-Xuse-experimental=kotlinx.serialization.InternalSerializationApi",
        "-Xuse-experimental=kotlinx.coroutines.ExperimentalCoroutinesApi",
        "-Xopt-in=kotlin.contracts.ExperimentalContracts",
        "-Xjvm-default=all",
    )
}