package me.adkhambek.taxi.ui.bottom.search

import me.adkhambek.taxi.datasource.models.AddressModel
import me.adkhambek.taxi.ui.bottom.BottomSheetConnector
import me.adkhambek.taxi.ui.bottom.search.SearchAddressContract.State
import org.orbitmvi.orbit.ContainerHost


interface SearchAddressContract {


    interface ViewModel : ContainerHost<State, SideEffect>, BottomSheetConnector {

        fun dispatch(intent: Intent)
    }

    data class State(
        val isLoading: Boolean,
        val listOfAddress: List<AddressModel>,
    )

    sealed interface Intent {

        data class SearchAddressIntent(val address: String) : Intent
        data class NavigateToOrder(val addressModel: AddressModel) : Intent
    }

    sealed interface SideEffect {

    }
}

fun State(): State = State(
    isLoading = false,
    listOfAddress = emptyList(),
)