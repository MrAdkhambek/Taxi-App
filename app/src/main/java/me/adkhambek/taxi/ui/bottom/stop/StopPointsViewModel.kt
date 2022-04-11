package me.adkhambek.taxi.ui.bottom.stop

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import me.adkhambek.taxi.datasource.models.Tariff
import me.adkhambek.taxi.ui.bottom.BottomSheetConnector
import me.adkhambek.taxi.ui.bottom.stop.StopPointsContract.SideEffect
import me.adkhambek.taxi.ui.bottom.stop.StopPointsContract.State
import me.adkhambek.taxi.utils.AddressRuntimeStore
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject


@HiltViewModel
class StopPointsViewModel @Inject constructor(
    private val addressRuntimeStore: AddressRuntimeStore,
    private val bottomSheetConnector: BottomSheetConnector,
) : ViewModel(),
    StopPointsContract.ViewModel,
    BottomSheetConnector by bottomSheetConnector {

    override val container: Container<State, SideEffect> = container(State()) {
        init()
        searchTariff()
    }

    private fun init() = intent(registerIdling = false) {
        addressRuntimeStore.addressesFlow.collectLatest {
            reduce {
                state.copy(
                    startPoint = it.first,
                    endPoint = it.second
                )
            }
        }
    }


    override fun dispatch(intent: StopPointsContract.Intent) {
        TODO("Not yet implemented")
    }

    private fun searchTariff() = intent {
        val list = (1..100).map {
            Tariff(
                title = "Эконом $it",
                subtitle = "14 900 cум"
            )
        }

        reduce {
            state.copy(listOfTariff = list)
        }
    }
}