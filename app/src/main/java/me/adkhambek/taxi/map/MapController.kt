package me.adkhambek.taxi.map

import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import dagger.assisted.AssistedFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import me.adkhambek.taxi.map.impl.MapControllerImpl
import me.adkhambek.taxi.utils.CoroutineDispatchers


interface MapCommandHolder {
    fun setController(controller: MapController)
    fun removeController()
}

interface MapCommandConnector {
    fun execute(command: MapController.Command)
}

interface MapController {

    val googleMap: GoogleMap
    val markers: HashMap<String, Marker>
    val eventFlow: Flow<MapControllerEvent>

    val coroutineScope: CoroutineScope
    val coroutineDispatchers: CoroutineDispatchers

    @AssistedFactory
    interface Factory {
        fun create(
            googleMap: GoogleMap
        ): MapControllerImpl
    }

    fun onCommand(command: Command)

    interface Command {

        val commandName: String
        suspend fun run(controller: MapController)

        companion object {

            @[JvmStatic JvmOverloads]
            operator fun invoke(
                commandName: String? = null,
                command: suspend MapController.() -> Unit,
            ) = object : Command {
                override val commandName: String = commandName ?: command::class.java.name
                override suspend fun run(controller: MapController) = command(controller)
            }
        }
    }

    sealed interface MapControllerEvent {

        data class ChangeCamEvent(val latLng: LatLng) : MapControllerEvent
    }
}


