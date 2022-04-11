package me.adkhambek.taxi.ui.bottom.favorite

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import me.adkhambek.taxi.datasource.models.AddressModel
import me.adkhambek.taxi.ui.bottom.BottomSheetConnector
import me.adkhambek.taxi.ui.bottom.BottomSheetState
import me.adkhambek.taxi.ui.bottom.favorite.FavoriteAddressContract.Intent.NavigateToOrder
import me.adkhambek.taxi.ui.bottom.favorite.FavoriteAddressContract.SideEffect
import me.adkhambek.taxi.ui.bottom.favorite.FavoriteAddressContract.State
import me.adkhambek.taxi.usecase.LoadHistoriesUseCase
import me.adkhambek.taxi.utils.AddressRuntimeStore
import me.adkhambek.taxi.utils.Try
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject


@HiltViewModel
internal class FavoriteAddressViewModel  @Inject constructor(
    private val loadHistoriesUseCase: LoadHistoriesUseCase,
    private val bottomSheetConnector: BottomSheetConnector,
    private val addressRuntimeStore: AddressRuntimeStore,
) : ViewModel(),
    FavoriteAddressContract.ViewModel,
    BottomSheetConnector by bottomSheetConnector {

    override val container: Container<State, SideEffect> = container(State()) {
        searchAddress("Toshkent") // TODO need to migrate database
    }

    override fun dispatch(intent: FavoriteAddressContract.Intent) = when(intent) {
        is NavigateToOrder -> navigateToOrder(intent.addressModel)
    }

    private fun searchAddress(address: String) = intent {
        when (val result = loadHistoriesUseCase(address)) {
            is Try.Failure -> postSideEffect(SideEffect.Toast(result.failure.message))
            is Try.Success -> reduce {
                state.copy(listOfAddress = result.data.candidates)
            }
        }
    }

    private fun navigateToOrder(addressModel: AddressModel) = intent {
        addressRuntimeStore.saveEndPointAddress(addressModel)
        changeBottomSheetState(BottomSheetState.StopPointState)
    }
}