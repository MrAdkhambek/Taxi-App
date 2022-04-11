package me.adkhambek.taxi.utils

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import me.adkhambek.taxi.datasource.models.AddressModel
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class AddressRuntimeStore @Inject constructor() {

    private var topAddress: AddressModel? = null
    private var bottomAddress: AddressModel? = null

    private val _addressesFlow: MutableStateFlow<Pair<AddressModel?, AddressModel?>> = MutableStateFlow(topAddress to bottomAddress)
    val addressesFlow: StateFlow<Pair<AddressModel?, AddressModel?>> = _addressesFlow.asStateFlow()

    suspend fun saveStartPointAddress(model: AddressModel) {
        topAddress = model
        val lastAddress = addressesFlow.value
        _addressesFlow.emit(model to lastAddress.second)
    }

    suspend fun saveEndPointAddress(model: AddressModel) {
        bottomAddress = model
        val lastAddress = addressesFlow.value
        _addressesFlow.emit(lastAddress.first to model)
    }
}