package me.adkhambek.taxi.di.navigation


import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.Cicerone.Companion.create
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@[Module InstallIn(SingletonComponent::class)]
object NavigationModule {

    private val cicerone: Cicerone<Router> = create()

    @[Provides Singleton]
    fun provideRouter(): Router = cicerone.router

    @[Provides Singleton]
    fun provideNavigatorHolder(): NavigatorHolder = cicerone.getNavigatorHolder()
}