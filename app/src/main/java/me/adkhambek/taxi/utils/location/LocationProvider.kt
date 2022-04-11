package me.adkhambek.taxi.utils.location

import android.location.Location
import kotlinx.coroutines.flow.Flow


interface LocationProvider {

    suspend operator fun invoke(): Location
    fun listen(): Flow<Location>
}