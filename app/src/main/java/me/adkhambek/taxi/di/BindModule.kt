package me.adkhambek.taxi.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import me.adkhambek.taxi.map.MapCommandConnector
import me.adkhambek.taxi.map.MapCommandHolder
import me.adkhambek.taxi.map.impl.MapCommandConnectorImpl
import me.adkhambek.taxi.utils.location.LocationProvider
import me.adkhambek.taxi.utils.location.LocationProviderImpl
import javax.inject.Singleton


@[Module InstallIn(SingletonComponent::class)]
interface BindModule {

    @[Binds Singleton]
    fun getLocationProvider(bind: LocationProviderImpl): LocationProvider

    @[Binds Singleton]
    fun getMapCommandConnector(bind: MapCommandConnectorImpl): MapCommandConnector

    @[Binds Singleton]
    fun getMapCommandHolder(bind: MapCommandConnectorImpl): MapCommandHolder
}