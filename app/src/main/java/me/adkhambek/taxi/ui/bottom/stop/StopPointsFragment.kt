package me.adkhambek.taxi.ui.bottom.stop

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import me.adkhambek.taxi.R
import me.adkhambek.taxi.databinding.FragmentStopPointsBinding
import me.adkhambek.taxi.ui.bottom.home.HomeContract
import me.adkhambek.taxi.ui.bottom.stop.StopPointsContract.SideEffect
import me.adkhambek.taxi.ui.bottom.stop.StopPointsContract.State
import me.adkhambek.taxi.ui.bottom.stop.adapter.TariffAdapter
import me.adkhambek.taxi.utils.BackButtonListener
import me.adkhambek.taxi.utils.diffing.diff
import me.adkhambek.taxi.utils.recycler.HorizontalSpacingItemDecorator
import org.orbitmvi.orbit.viewmodel.observe


@AndroidEntryPoint
class StopPointsFragment : Fragment(R.layout.fragment_stop_points), BackButtonListener {

    private val binding by viewBinding(FragmentStopPointsBinding::bind)
    private val viewModel: StopPointsContract.ViewModel by viewModels<StopPointsViewModel>()

    private lateinit var tariffAdapter: TariffAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupIntents()
        setupRecyclerView()

        val renderer = diff<State> {
            diff(get = State::listOfTariff, set = tariffAdapter::submitList)


            diff(get = State::startPoint) {
                binding
                    .staticLayout
                    .topTextView
                    .text = it?.address ?: getString(R.string.choose_a_place)
            }

            diff(get = State::endPoint) {
                binding
                    .staticLayout
                    .bottomTextView
                    .text = it?.formattedAddress ?: getString(R.string.where)
            }
        }

        viewModel
            .observe(
                state = renderer::render,
                sideEffect = this::handleSideEffect,
                lifecycleOwner = viewLifecycleOwner,
            )
    }

    private fun setupRecyclerView() = with(binding.mainRecyclerView) {
        layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        tariffAdapter = TariffAdapter()
        adapter = tariffAdapter

        addItemDecoration(
            HorizontalSpacingItemDecorator(resources.getDimension(R.dimen.theme_space))
        )
    }

    private fun setupIntents() = with(binding) {

    }

    private fun handleSideEffect(effect: SideEffect) {

    }

    override fun onDestroyView() {
        binding.mainRecyclerView.adapter = null
        super.onDestroyView()
    }

    override fun onBackPressed(): Boolean = viewModel.onBackPressed()
}