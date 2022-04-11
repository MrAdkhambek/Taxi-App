package me.adkhambek.taxi.ui.bottom.home

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import me.adkhambek.taxi.datasource.models.Delivery
import me.adkhambek.taxi.datasource.models.Express
import me.adkhambek.taxi.datasource.models.History
import me.adkhambek.taxi.datasource.models.Interesting
import me.adkhambek.taxi.ui.bottom.BottomSheetConnector
import me.adkhambek.taxi.ui.bottom.BottomSheetState
import me.adkhambek.taxi.ui.bottom.home.HomeContract.SideEffect
import me.adkhambek.taxi.ui.bottom.home.HomeContract.State
import me.adkhambek.taxi.utils.AddressRuntimeStore
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val addressRuntimeStore: AddressRuntimeStore,
    private val bottomSheetConnector: BottomSheetConnector,
) : ViewModel(),
    HomeContract.ViewModel,
    BottomSheetConnector by bottomSheetConnector {

    override val container: Container<State, SideEffect> = container(State()) {
        init()
    }

    private fun init() = intent(registerIdling = false) {
        delay(1000)
        val list = (1L..4L).map {
            History(
                id = it,
                time = "18мин",
                title = "Work"
            )
        }
            .plus(Express)
            .plus(Delivery)
            .plus(Interesting)

        reduce {
            state.copy(
                listOfBaseItem = list
            )
        }

        addressRuntimeStore.addressesFlow.collectLatest {
            reduce {
                state.copy(
                    startPoint = it.first,
                    endPoint = it.second
                )
            }
        }
    }

    override fun dispatch(intent: HomeContract.Intent) = when (intent) {
        HomeContract.Intent.NavigateToSearch -> navigateToSearch()
    }

    private fun navigateToSearch() = intent {
        changeBottomSheetState(BottomSheetState.SearchState)
    }
}