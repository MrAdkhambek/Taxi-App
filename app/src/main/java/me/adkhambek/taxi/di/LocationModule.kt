package me.adkhambek.taxi.di

import android.app.Application
import android.content.Context
import android.location.Geocoder
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.util.*
import javax.inject.Singleton


@[Module InstallIn(SingletonComponent::class)]
open class LocationModule {

    @[Provides Singleton]
    open fun getLocationRequest(): LocationRequest {
        return LocationRequest.create().apply {
            interval = 2 * 1000
            fastestInterval = 2 * 1000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            maxWaitTime = 100
        }
    }

    @[Provides Singleton]
    open fun getFusedClient(
        @ApplicationContext context: Context,
    ): FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)

    @[Provides Singleton]
    open fun getGeocoder(application: Application): Geocoder {
        return Geocoder(application, Locale.getDefault())
    }
}