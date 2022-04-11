package me.adkhambek.taxi.datasource.local

import dagger.Lazy
import me.adkhambek.taxi.datasource.models.SearchResponse
import me.adkhambek.taxi.datasource.remote.MainAPI
import retrofit2.Response
import javax.inject.Inject


class MainRepository @Inject constructor(
    private val mainAPI: Lazy<MainAPI>,
) {


    suspend fun search(query: String): Response<SearchResponse> {
        return mainAPI.get().search(
            limit = 20,
            query = query
        )
    }
}