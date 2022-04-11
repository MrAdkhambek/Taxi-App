package me.adkhambek.taxi.datasource.models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class BaseResponse<T>(

    @SerialName("data")
    val `data`: T?,

    @SerialName("status")
    val status: String
)