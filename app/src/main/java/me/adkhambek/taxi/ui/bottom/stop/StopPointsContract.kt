package me.adkhambek.taxi.ui.bottom.stop

import me.adkhambek.taxi.datasource.models.Tariff
import me.adkhambek.taxi.ui.bottom.BottomSheetConnector
import me.adkhambek.taxi.ui.bottom.stop.StopPointsContract.State
import org.orbitmvi.orbit.ContainerHost


interface StopPointsContract {


    interface ViewModel : ContainerHost<State, SideEffect>, BottomSheetConnector {

        fun dispatch(intent: Intent)
    }

    data class State(
        val isLoading: Boolean,
        val listOfAddress: List<Tariff>,
    )

    sealed interface Intent {

    }

    sealed interface SideEffect {

    }
}

fun State(): State = State(
    isLoading = false,
    listOfAddress = emptyList(),
)