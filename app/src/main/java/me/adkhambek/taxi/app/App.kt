package me.adkhambek.taxi.app

import android.app.Application
import com.pluto.Pluto
import com.pluto.plugins.network.PlutoNetworkPlugin
import dagger.hilt.android.HiltAndroidApp
import me.adkhambek.taxi.BuildConfig
import timber.log.Timber


@HiltAndroidApp
class App : Application() {


    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())

            Pluto.Installer(this)
                .addPlugin(PlutoNetworkPlugin("NETWORK"))
                .install()
            Pluto.showNotch(true)
        }
    }
}