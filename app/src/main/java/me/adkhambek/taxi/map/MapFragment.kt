package me.adkhambek.taxi.map

import android.location.Geocoder
import android.os.Bundle
import android.view.View
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.ktx.awaitMap
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onEach
import me.adkhambek.taxi.datasource.models.AddressModel
import me.adkhambek.taxi.ktx.viewLifecycleScope
import me.adkhambek.taxi.map.MapController.MapControllerEvent.ChangeCamEvent
import me.adkhambek.taxi.utils.AddressRuntimeStore
import timber.log.Timber
import javax.inject.Inject


class MapFragment @Inject constructor(
    private val geocoder: Geocoder,
    private val mapCommandHolder: MapCommandHolder,
    private val addressRuntimeStore: AddressRuntimeStore,
    private val mapControllerFactory: MapController.Factory
) : SupportMapFragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleScope.launchWhenCreated {
            val map = awaitMap()
            val mapController: MapController = mapControllerFactory.create(map)
            mapCommandHolder.setController(mapController)

            mapController
                .eventFlow
                .onEach {
                    when (it) {
                        is ChangeCamEvent -> onChangeCam(it.latLng)
                    }
                }
                .flowOn(Dispatchers.IO)
                .catch { Timber.tag("geocoder").e(it) }
                .collect()
        }
    }

    private suspend fun onChangeCam(latLng: LatLng) {
        val geoData = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1).firstOrNull() ?: return
        val addressModel = AddressModel(
            title = geoData.thoroughfare.orEmpty(),
            subtitle = geoData.thoroughfare.orEmpty(),
        )

        Timber.tag("geocoder").d(addressModel.toString())
        addressRuntimeStore.saveStartPointAddress(addressModel)
    }

    override fun onDestroyView() {
        mapCommandHolder.removeController()
        super.onDestroyView()
    }
}