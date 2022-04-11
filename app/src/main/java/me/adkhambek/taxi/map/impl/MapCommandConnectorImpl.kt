package me.adkhambek.taxi.map.impl

import me.adkhambek.taxi.map.MapCommandConnector
import me.adkhambek.taxi.map.MapCommandHolder
import me.adkhambek.taxi.map.MapController
import me.adkhambek.taxi.map.MapController.Command
import java.util.concurrent.ConcurrentLinkedQueue
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class MapCommandConnectorImpl @Inject constructor() : MapCommandConnector, MapCommandHolder {

    private var controller: MapController? = null
    private val pendingCommands: ConcurrentLinkedQueue<Command> = ConcurrentLinkedQueue<Command>()

    override fun setController(controller: MapController) {
        this.controller = controller

        while (this.controller != null) {
            val command = pendingCommands.poll() ?: break
            controller.onCommand(command)
        }
    }

    override fun removeController() {
        controller = null
    }

    override fun execute(command: Command) {
        controller?.onCommand(command) ?: pendingCommands.add(command)
    }
}