package me.adkhambek.taxi.ui.bottom.search

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import me.adkhambek.taxi.datasource.models.AddressModel
import me.adkhambek.taxi.ui.bottom.BottomSheetConnector
import me.adkhambek.taxi.ui.bottom.BottomSheetState
import me.adkhambek.taxi.ui.bottom.search.SearchAddressContract.SideEffect
import me.adkhambek.taxi.usecase.SearchAddressUseCase
import me.adkhambek.taxi.utils.AddressRuntimeStore
import me.adkhambek.taxi.utils.Try
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject


@HiltViewModel
class SearchAddressViewModel @Inject constructor(
    private val bottomSheetConnector: BottomSheetConnector,
    private val searchAddressUseCase: SearchAddressUseCase,
    private val addressRuntimeStore: AddressRuntimeStore,
) : ViewModel(),
    SearchAddressContract.ViewModel,
    BottomSheetConnector by bottomSheetConnector {

    override val container: Container<SearchAddressContract.State, SideEffect> = container(State()) {
        searchAddress(String())
    }

    override fun dispatch(intent: SearchAddressContract.Intent) = when (intent) {
        is SearchAddressContract.Intent.SearchAddressIntent -> searchAddress(intent.address)
        is SearchAddressContract.Intent.NavigateToOrder -> navigateToOrder(intent.addressModel)
    }

    private fun searchAddress(address: String) = intent {
        when (val result = searchAddressUseCase(address)) {
            is Try.Failure -> postSideEffect(SideEffect.Toast(result.failure.message))
            is Try.Success -> reduce {
                state.copy(listOfAddress = result.data.candidates)
            }
        }
    }

    private fun navigateToOrder(addressModel: AddressModel) = intent {
        addressRuntimeStore.saveEndPointAddress(addressModel)
        changeBottomSheetState(BottomSheetState.StopPointState)
    }
}