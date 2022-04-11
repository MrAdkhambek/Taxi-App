package me.adkhambek.taxi.ui.bottom.favorite

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import me.adkhambek.taxi.R
import me.adkhambek.taxi.base.AppBottomSheetDialogFragment
import me.adkhambek.taxi.databinding.FavoriteAddressFragmentBinding
import me.adkhambek.taxi.datasource.models.AddressModel
import me.adkhambek.taxi.ui.bottom.favorite.FavoriteAddressContract.Intent.NavigateToOrder
import me.adkhambek.taxi.ui.bottom.favorite.FavoriteAddressContract.State
import me.adkhambek.taxi.ui.bottom.favorite.adapter.FavoriteAddressAdapter
import me.adkhambek.taxi.utils.diffing.diff
import org.orbitmvi.orbit.viewmodel.observe


@AndroidEntryPoint
class FavoriteAddressFragment :
    AppBottomSheetDialogFragment(R.layout.favorite_address_fragment),
    FavoriteAddressAdapter.Listener {

    private val binding by viewBinding(FavoriteAddressFragmentBinding::bind)
    private val viewModel: FavoriteAddressContract.ViewModel by viewModels<FavoriteAddressViewModel>()

    private lateinit var favoriteAddressAdapter: FavoriteAddressAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupIntents()
        setupRecyclerView()

        val renderer = diff<State> {
            diff(get = State::listOfAddress, set = favoriteAddressAdapter::submitList)
        }

        viewModel
            .observe(
                state = renderer::render,
                sideEffect = this::handleSideEffect,
                lifecycleOwner = viewLifecycleOwner,
            )
    }

    private fun setupRecyclerView() = with(binding.recyclerView) {
        favoriteAddressAdapter = FavoriteAddressAdapter(this@FavoriteAddressFragment)
        layoutManager = LinearLayoutManager(context)
        adapter = favoriteAddressAdapter
    }

    private fun setupIntents() = with(binding) {

    }


    private fun handleSideEffect(effect: FavoriteAddressContract.SideEffect) {

    }


    override fun onClickAddress(item: AddressModel?) {
        item ?: return
        dismiss()
        viewModel.dispatch(NavigateToOrder(item))
    }

    override fun onDestroyView() {
        binding.recyclerView.adapter = null
        super.onDestroyView()
    }
}