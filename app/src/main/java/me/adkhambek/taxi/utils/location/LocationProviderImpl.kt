package me.adkhambek.taxi.utils.location

import android.location.Location
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import dagger.Lazy
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import me.adkhambek.taxi.ktx.asFlow
import javax.inject.Inject
import javax.inject.Provider


class LocationProviderImpl @Inject constructor(
    private val locationRequest: Provider<LocationRequest>,
    private val fusedLocationProviderClient: Lazy<FusedLocationProviderClient>,
) : LocationProvider {

    override suspend fun invoke(): Location = listen().first()

    override fun listen(): Flow<Location> =
        fusedLocationProviderClient.get().asFlow(
            locationRequest = locationRequest.get()
        )
}