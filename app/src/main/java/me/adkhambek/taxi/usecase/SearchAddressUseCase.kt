package me.adkhambek.taxi.usecase

import dagger.Lazy
import kotlinx.coroutines.withContext
import me.adkhambek.taxi.datasource.local.MainRepository
import me.adkhambek.taxi.datasource.models.SearchResult
import me.adkhambek.taxi.utils.CoroutineDispatchers
import me.adkhambek.taxi.utils.ResponseHandler
import me.adkhambek.taxi.utils.Try
import me.adkhambek.taxi.utils.location.LocationProvider
import javax.inject.Inject


class SearchAddressUseCase @Inject constructor(
    private val repository: Lazy<MainRepository>,
    private val responseHandler: ResponseHandler,
    private val locationProvider: LocationProvider,
    private val coroutineDispatchers: CoroutineDispatchers,
) {

    suspend operator fun invoke(query: String): Try<SearchResult> = withContext(coroutineDispatchers.IO) {
        responseHandler.proceed {
            repository.get().search(query)
        }
    }
}