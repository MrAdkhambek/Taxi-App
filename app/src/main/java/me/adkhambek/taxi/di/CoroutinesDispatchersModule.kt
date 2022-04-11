package me.adkhambek.taxi.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import me.adkhambek.taxi.utils.CoroutineDispatchers


@[Module InstallIn(SingletonComponent::class)]
object CoroutinesDispatchersModule {

    @[Provides]
    fun providesCoroutineDispatchers(): CoroutineDispatchers = object : CoroutineDispatchers {
        override val IO: CoroutineDispatcher get() = Dispatchers.IO
        override val MAIN: CoroutineDispatcher get() = Dispatchers.Main
        override val DEFAULT: CoroutineDispatcher get() = Dispatchers.Default
        override val UNCONFINED: CoroutineDispatcher get() = Dispatchers.Unconfined
    }
}