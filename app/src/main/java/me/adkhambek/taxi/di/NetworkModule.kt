package me.adkhambek.taxi.di


import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.pluto.plugins.network.PlutoInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import kotlinx.serialization.json.Json
import me.adkhambek.taxi.BuildConfig
import me.adkhambek.taxi.datasource.remote.MainAPI
import me.adkhambek.taxi.datasource.remote.interceptor.AppInterceptor
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.create
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Provider
import javax.inject.Singleton


@[Module InstallIn(SingletonComponent::class)]
object NetworkModule {

    @[Provides IntoSet]
    fun provideChuckerCollector(): Interceptor = PlutoInterceptor()

    @[Provides IntoSet]
    fun provideAppInterceptor(): Interceptor = AppInterceptor()

    @[Provides IntoSet]
    fun provideHttpLoggingInterceptor(): Interceptor =
        HttpLoggingInterceptor(Timber.tag("NETWORK")::d).apply {
            level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
            else HttpLoggingInterceptor.Level.NONE
        }

    @[Provides Singleton]
    fun provideJson(): Json = Json {
        prettyPrint = BuildConfig.DEBUG
    }

    fun provideFactory(json: Json): Converter.Factory {
        val contentType = "application/json".toMediaType()
        return json.asConverterFactory(contentType)
    }

    @[Provides Singleton]
    fun provideRetrofit(
        jsonProvider: Provider<Json>,
        clientProvider: Provider<OkHttpClient>,
    ): Retrofit = Retrofit.Builder()
        .baseUrl("https://testrouting.mytaxi.uz")
        .client(clientProvider.get())
        .addConverterFactory(provideFactory(jsonProvider.get()))
        .build()

    @[Provides Singleton]
    fun provideClient(
        interceptors: Set<@JvmSuppressWildcards Interceptor>,
    ): OkHttpClient {
        val builder = OkHttpClient().newBuilder()
            .callTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)

        interceptors.forEach(builder::addInterceptor)
        return builder.build()
    }

    @[Provides Singleton]
    fun provideMainAPI(
        retrofit: Provider<Retrofit>
    ): MainAPI = retrofit.get().create()
}