package me.adkhambek.taxi.ui.bottom.favorite

import me.adkhambek.taxi.datasource.models.AddressModel
import me.adkhambek.taxi.ui.bottom.BottomSheetConnector
import me.adkhambek.taxi.ui.bottom.favorite.FavoriteAddressContract.State
import me.adkhambek.taxi.utils.Text
import org.orbitmvi.orbit.ContainerHost


internal interface FavoriteAddressContract {

    interface ViewModel : ContainerHost<State, SideEffect>, BottomSheetConnector {
        fun dispatch(intent: Intent)
    }

    data class State(
        val isLoading: Boolean,
        val listOfAddress: List<AddressModel>,
    )

    sealed interface Intent {

        data class NavigateToOrder(val addressModel: AddressModel) : Intent
    }

    sealed interface SideEffect {
        data class Toast(val message: Text) : SideEffect
    }
}

internal fun State(): State = State(
    isLoading = false,
    listOfAddress = emptyList(),
)