package me.adkhambek.taxi.di.navigation


import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import me.adkhambek.taxi.ui.bottom.BottomSheetConnector
import me.adkhambek.taxi.ui.bottom.BottomSheetConnectorImpl
import me.adkhambek.taxi.ui.bottom.home.HomeContract
import me.adkhambek.taxi.ui.bottom.home.HomeRouter
import javax.inject.Singleton


@[Module InstallIn(SingletonComponent::class)]
interface BindModule {

    @[Binds Singleton]
    fun bindBottomSheetConnector(router: BottomSheetConnectorImpl): BottomSheetConnector
}