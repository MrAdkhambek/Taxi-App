package me.adkhambek.taxi.ui.bottom


sealed interface BottomSheetState {

    object MainState : BottomSheetState
    object SearchState : BottomSheetState
    object StopPointState : BottomSheetState
}