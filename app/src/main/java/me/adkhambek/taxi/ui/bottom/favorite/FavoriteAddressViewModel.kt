package me.adkhambek.taxi.ui.bottom.favorite

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import me.adkhambek.taxi.datasource.models.AddressModel
import me.adkhambek.taxi.ui.bottom.BottomSheetConnector
import me.adkhambek.taxi.ui.bottom.BottomSheetState
import me.adkhambek.taxi.ui.bottom.favorite.FavoriteAddressContract.Intent.*
import me.adkhambek.taxi.ui.bottom.favorite.FavoriteAddressContract.SideEffect
import me.adkhambek.taxi.ui.bottom.favorite.FavoriteAddressContract.State
import me.adkhambek.taxi.ui.bottom.search.SearchAddressContract
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject


@HiltViewModel
internal class FavoriteAddressViewModel  @Inject constructor(
    private val bottomSheetConnector: BottomSheetConnector,
) : ViewModel(),
    FavoriteAddressContract.ViewModel,
    BottomSheetConnector by bottomSheetConnector {

    override val container: Container<State, SideEffect> = container(State()) {
        searchAddress("adla")
    }

    override fun dispatch(intent: FavoriteAddressContract.Intent) = when(intent) {
        is NavigateToOrder -> navigateToOrder(intent.addressModel)
    }

    private fun searchAddress(address: String) = intent {
        val list = (1..4).map {
            AddressModel(
                title= "$address $it",
                subtitle= "Address $it"
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