@file:Suppress("FunctionName")
package me.adkhambek.taxi.map.command

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import me.adkhambek.taxi.map.MapController


fun MoveCameraCommand(
    latLng: LatLng,
    zoom: Float = 12f,
) = MapController.Command {
    val cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, zoom)
    googleMap.animateCamera(cameraUpdate)
}