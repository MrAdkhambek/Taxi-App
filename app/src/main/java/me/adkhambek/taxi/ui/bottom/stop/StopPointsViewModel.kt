package me.adkhambek.taxi.ui.bottom.stop

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import me.adkhambek.taxi.datasource.models.Tariff
import me.adkhambek.taxi.ui.bottom.BottomSheetConnector
import me.adkhambek.taxi.ui.bottom.stop.StopPointsContract.SideEffect
import me.adkhambek.taxi.ui.bottom.stop.StopPointsContract.State
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject


@HiltViewModel
class StopPointsViewModel @Inject constructor(
    private val bottomSheetConnector: BottomSheetConnector,
) : ViewModel(),
    StopPointsContract.ViewModel,
    BottomSheetConnector by bottomSheetConnector {

    override val container: Container<State, SideEffect> = container(State()) { searchAddress() }


    override fun dispatch(intent: StopPointsContract.Intent) {
        TODO("Not yet implemented")
    }

    private fun searchAddress() = intent {
        val list = (1..100).map {
            Tariff(
                title = "Эконом $it",
                subtitle = "14 900 cум"
            )
        }

        reduce {
            state.copy(listOfAddress = list)
        }
    }
}