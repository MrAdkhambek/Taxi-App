package me.adkhambek.taxi.ktx

import android.annotation.SuppressLint
import android.location.Location
import android.os.Looper
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow


@SuppressLint("MissingPermission")
fun FusedLocationProviderClient.asFlow(
    locationRequest: LocationRequest,
): Flow<Location> = callbackFlow<Location> {

    val callback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            locationResult.locations.forEach(::trySend)
        }
    }

    requestLocationUpdates(locationRequest, callback, Looper.getMainLooper())
    awaitClose { removeLocationUpdates(callback) }
}

fun Location.toLatLng(): LatLng = LatLng(
    this.latitude,
    this.longitude,
)