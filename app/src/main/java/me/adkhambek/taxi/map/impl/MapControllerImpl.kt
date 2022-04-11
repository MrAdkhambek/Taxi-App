package me.adkhambek.taxi.map.impl

import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import me.adkhambek.taxi.map.MapController
import me.adkhambek.taxi.map.MapController.MapControllerEvent
import me.adkhambek.taxi.map.MapController.MapControllerEvent.ChangeCamEvent
import me.adkhambek.taxi.utils.CoroutineDispatchers
import timber.log.Timber


class MapControllerImpl constructor(
    override val googleMap: GoogleMap,
    override val coroutineScope: CoroutineScope,
    override val coroutineDispatchers: CoroutineDispatchers,
) : MapController {

    override val markers: HashMap<String, Marker> = hashMapOf()
    override val eventFlow: MutableSharedFlow<MapControllerEvent> = MutableSharedFlow()

    @AssistedInject
    constructor(
        @Assisted googleMap: GoogleMap,
        coroutineDispatchers: CoroutineDispatchers,
    ) : this(
        googleMap = googleMap,
        coroutineDispatchers = coroutineDispatchers,
        coroutineScope = CoroutineScope(coroutineDispatchers.MAIN)
    ) {

        googleMap.setOnCameraIdleListener {
            val latLng = googleMap.cameraPosition.target
            coroutineScope.launch {
                val event = ChangeCamEvent(latLng)
                eventFlow.emit(event)
            }
        }
    }

    override fun onCommand(command: MapController.Command) {
        coroutineScope.launch {
            Timber.tag("MapController: run - ").d(command.commandName)
            command.run(this@MapControllerImpl)
        }
    }
}