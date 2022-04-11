@file:Suppress("FunctionName")

package me.adkhambek.taxi.ui.bottom

import com.github.terrakok.cicerone.Screen
import com.github.terrakok.cicerone.androidx.FragmentScreen
import me.adkhambek.taxi.ui.bottom.home.HomeFragment
import me.adkhambek.taxi.ui.bottom.search.SearchAddressFragment
import me.adkhambek.taxi.ui.bottom.stop.StopPointsFragment


object BottomScreens {

    @JvmStatic
    fun Home(): Screen = FragmentScreen {
        HomeFragment()
    }

    @JvmStatic
    fun Search(): Screen = FragmentScreen {
        SearchAddressFragment()
    }

    @JvmStatic
    fun StopPoints(): Screen = FragmentScreen {
        StopPointsFragment()
    }
}