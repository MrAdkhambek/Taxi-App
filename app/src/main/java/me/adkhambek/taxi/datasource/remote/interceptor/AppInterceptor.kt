package me.adkhambek.taxi.datasource.remote.interceptor


import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response


class AppInterceptor constructor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val original: Request = chain.request()

        val requestBuilder = original.newBuilder()
            .header("api_key", "679e096b28bffbb5d6c3a888ecfe8cf46343eee3")

        val request = requestBuilder.build()
        return chain.proceed(request)
    }
}