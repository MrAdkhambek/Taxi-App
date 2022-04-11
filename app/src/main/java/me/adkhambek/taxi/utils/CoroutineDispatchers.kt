package me.adkhambek.taxi.utils

import kotlinx.coroutines.CoroutineDispatcher


interface CoroutineDispatchers {
    val IO: CoroutineDispatcher
    val MAIN: CoroutineDispatcher
    val DEFAULT: CoroutineDispatcher
    val UNCONFINED: CoroutineDispatcher
}