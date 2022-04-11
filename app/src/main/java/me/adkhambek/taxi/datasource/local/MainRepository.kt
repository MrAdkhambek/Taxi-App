package me.adkhambek.taxi.datasource.local

import dagger.Lazy
import kotlinx.coroutines.flow.first
import me.adkhambek.taxi.datasource.models.SearchResponse
import me.adkhambek.taxi.datasource.remote.MainAPI
import me.adkhambek.taxi.utils.AddressRuntimeStore
import retrofit2.Response
import javax.inject.Inject


class MainRepository @Inject constructor(
    private val mainAPI: Lazy<MainAPI>,
    private val addressRuntimeStore: AddressRuntimeStore,
) {

    suspend fun search(query: String): Response<SearchResponse> {
        val addressModel = addressRuntimeStore.addressesFlow.first().first
        val location = addressModel?.location

        return mainAPI.get().search(
            limit = 20,
            query = query,
            lat = location?.lat,
            lng = location?.lon
        )
    }
}