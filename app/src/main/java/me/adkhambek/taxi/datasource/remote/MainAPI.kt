package me.adkhambek.taxi.datasource.remote

import me.adkhambek.taxi.datasource.models.SearchResponse
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query


interface MainAPI {

    @GET("/v2/search")
    suspend fun search(
        @Query("limit") limit: Long,
        @Query("query") query: String,
        @Query("lat") lat: Double?,
        @Query("lng") lng: Double?,
    ): Response<SearchResponse>
}