package me.adkhambek.taxi.ui

import me.adkhambek.taxi.ui.MainContract.State
import me.adkhambek.taxi.ui.bottom.BottomSheetConnector
import me.adkhambek.taxi.ui.bottom.BottomSheetState
import org.orbitmvi.orbit.ContainerHost


interface MainContract {

    interface ViewModel : ContainerHost<State, SideEffect>, BottomSheetConnector {

        fun dispatch(intent: Intent)
    }

    data class State(
        val slideOffset: Float,
        val isLoading: Boolean,
        val bottomSheetState: BottomSheetState,
    )

    sealed interface Intent {
        object ShowFavoriteAddress : Intent
        data class ChangeBottomOffset(val slideOffset: Float) : Intent
    }

    sealed interface SideEffect {
        object ShowFavoriteAddress : SideEffect
    }
}

fun State(): State = State(
    isLoading = false,
    slideOffset = 0f,
    bottomSheetState = BottomSheetState.MainState
)