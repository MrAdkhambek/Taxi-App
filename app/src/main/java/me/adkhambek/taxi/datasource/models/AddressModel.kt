package me.adkhambek.taxi.datasource.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


typealias SearchResponse = BaseResponse<SearchResult>

@Serializable
data class SearchResult(
    @SerialName("candidates") val candidates: List<AddressModel>,
)

@Serializable
data class AddressModel(
    @SerialName("formatted_address") val formattedAddress: String,
    @SerialName("street_poi_id") val streetPoiId: Double,
    @SerialName("address_id") val addressId: Double,
    @SerialName("location") val location: LatLon,
    @SerialName("address") val address: String,
    @SerialName("lang") val lang: String,
)

@Serializable
data class LatLon(
    @SerialName("lat") val lat: Double,
    @SerialName("lng") val lon: Double,
)