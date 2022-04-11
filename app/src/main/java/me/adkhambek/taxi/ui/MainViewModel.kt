package me.adkhambek.taxi.ui

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import me.adkhambek.taxi.ktx.toLatLng
import me.adkhambek.taxi.map.MapCommandConnector
import me.adkhambek.taxi.map.command.MoveCameraCommand
import me.adkhambek.taxi.ui.MainContract.Intent.ChangeBottomOffset
import me.adkhambek.taxi.ui.MainContract.SideEffect
import me.adkhambek.taxi.ui.MainContract.State
import me.adkhambek.taxi.ui.bottom.BottomSheetConnector
import me.adkhambek.taxi.utils.location.LocationProvider
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    private val locationProvider: LocationProvider,
    private val mapCommandConnector: MapCommandConnector,
    private val bottomSheetConnector: BottomSheetConnector,
) : ViewModel(),
    MainContract.ViewModel,
    BottomSheetConnector by bottomSheetConnector {

    override val container: Container<State, SideEffect> = container(State()) {
        loadFistLocation()
        listenBottomSheetState()
    }

    private fun loadFistLocation() = intent(registerIdling = false) {
        val location = locationProvider()
        mapCommandConnector.execute(MoveCameraCommand(location.toLatLng()))
    }

    private fun listenBottomSheetState() = intent(registerIdling = false) {
        bottomSheetStateFlow.collectLatest { newBottomSheetState ->
            reduce { state.copy(bottomSheetState = newBottomSheetState) }
        }
    }

    override fun dispatch(intent: MainContract.Intent) = when (intent) {
        is ChangeBottomOffset -> onChangeBottomOffset(intent.slideOffset)
        MainContract.Intent.ShowFavoriteAddress -> onShowFavoriteAddressIntent()
    }

    private fun onShowFavoriteAddressIntent() = intent {
        postSideEffect(SideEffect.ShowFavoriteAddress)
    }

    private fun onChangeBottomOffset(slideOffset: Float) = intent {
        reduce {
            state.copy(slideOffset = slideOffset)
        }
    }
}