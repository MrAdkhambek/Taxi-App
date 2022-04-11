package me.adkhambek.taxi.ui.bottom.home

import me.adkhambek.taxi.datasource.models.AddressModel
import me.adkhambek.taxi.datasource.models.BaseMainItem
import me.adkhambek.taxi.ui.bottom.BottomSheetConnector
import me.adkhambek.taxi.ui.bottom.home.HomeContract.State
import org.orbitmvi.orbit.ContainerHost


interface HomeContract {

    interface Router {

        suspend fun navigateSearch()
    }

    interface ViewModel : ContainerHost<State, SideEffect>, BottomSheetConnector {

        fun dispatch(intent: Intent)
    }

    data class State(
        val isLoading: Boolean,
        val listOfBaseItem: List<BaseMainItem>,

        val startPoint: AddressModel?,
        val endPoint: AddressModel?
    )

    sealed interface Intent {

        object NavigateToSearch : Intent
    }

    sealed interface SideEffect {

    }
}

fun State(): State = State(
    isLoading = false,
    listOfBaseItem = emptyList(),

    startPoint = null,
    endPoint = null,
)