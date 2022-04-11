package me.adkhambek.taxi.ui.bottom

import com.github.terrakok.cicerone.Router
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import me.adkhambek.taxi.utils.BackButtonListener
import javax.inject.Inject


interface BottomSheetConnector : BackButtonListener {

    val bottomSheetStateFlow: StateFlow<BottomSheetState>
    suspend fun changeBottomSheetState(state: BottomSheetState)
}


class BottomSheetConnectorImpl @Inject constructor(
    private val router: Router
) : BottomSheetConnector {

    override val bottomSheetStateFlow: MutableStateFlow<BottomSheetState> = MutableStateFlow(BottomSheetState.MainState)

    override suspend fun changeBottomSheetState(state: BottomSheetState) {
        bottomSheetStateFlow.emit(state)

        when (state) {
            BottomSheetState.MainState -> router.newRootChain(BottomScreens.Home())
            BottomSheetState.SearchState -> router.navigateTo(BottomScreens.Search())
            BottomSheetState.StopPointState -> router.navigateTo(BottomScreens.StopPoints())
        }
    }

    override fun onBackPressed(): Boolean {
        return when (bottomSheetStateFlow.value) {
            BottomSheetState.MainState -> false
            BottomSheetState.SearchState -> {
                bottomSheetStateFlow.value = BottomSheetState.MainState
                router.exit()
                return true
            }
            BottomSheetState.StopPointState -> {
                bottomSheetStateFlow.value = BottomSheetState.SearchState
                router.exit()
                return true
            }
        }
    }
}