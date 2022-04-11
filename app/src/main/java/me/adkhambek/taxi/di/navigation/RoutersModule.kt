package me.adkhambek.taxi.di.navigation


import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import me.adkhambek.taxi.ui.bottom.home.HomeContract
import me.adkhambek.taxi.ui.bottom.home.HomeRouter


@[Module InstallIn(SingletonComponent::class)]
interface RoutersModule {

    @[Binds]
    fun bindRouter(router: HomeRouter): HomeContract.Router
}