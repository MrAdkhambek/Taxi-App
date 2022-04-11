package me.adkhambek.taxi.ui.bottom.search

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import me.adkhambek.taxi.datasource.models.AddressModel
import me.adkhambek.taxi.ui.bottom.BottomSheetConnector
import me.adkhambek.taxi.ui.bottom.BottomSheetState
import me.adkhambek.taxi.ui.bottom.search.SearchAddressContract.SideEffect
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject


@HiltViewModel
class SearchAddressViewModel @Inject constructor(
    private val bottomSheetConnector: BottomSheetConnector,
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
        val list = (1..100).map {
            AddressModel(
                title = "$address $it",
                subtitle = "Address $it"
            )
        }

        reduce {
            state.copy(listOfAddress = list)
        }
    }

    private fun navigateToOrder(addressModel: AddressModel) = intent {
        changeBottomSheetState(BottomSheetState.StopPointState)
    }
}