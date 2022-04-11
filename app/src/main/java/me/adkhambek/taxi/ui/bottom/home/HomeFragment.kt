package me.adkhambek.taxi.ui.bottom.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import me.adkhambek.taxi.R
import me.adkhambek.taxi.databinding.FragmentHomeBinding
import me.adkhambek.taxi.ui.bottom.home.HomeContract.State
import me.adkhambek.taxi.ui.bottom.home.HomeContract.ViewModel
import me.adkhambek.taxi.ui.bottom.home.adapter.main.MainBottomAdapter
import me.adkhambek.taxi.utils.BackButtonListener
import me.adkhambek.taxi.utils.diffing.diff
import me.adkhambek.taxi.utils.recycler.GridSpacingItemDecoration
import org.orbitmvi.orbit.viewmodel.observe
import javax.inject.Inject


@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home), BackButtonListener {

    private val binding by viewBinding(FragmentHomeBinding::bind)
    private val viewModel: ViewModel by viewModels<HomeViewModel>()

    @Inject lateinit var mainAdapter: MainBottomAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupIntents()
        setupRecyclerView()

        val renderer = diff<State> {
            diff(get = State::listOfBaseItem, set = mainAdapter::submitList)

            diff(get = State::startPoint) {
                binding
                    .staticLayout
                    .topTextView
                    .text = it?.title ?: getString(R.string.choose_a_place)
            }

            diff(get = State::endPoint) {
                binding
                    .staticLayout
                    .bottomTextView
                    .text = it?.title ?: getString(R.string.where)
            }
        }

        viewModel
            .observe(
                state = renderer::render,
                sideEffect = this::handleSideEffect,
                lifecycleOwner = viewLifecycleOwner,
            )
    }

    private fun setupIntents() = with(binding) {
        staticLayout.topLine.setOnClickListener {}

        staticLayout.bottomLine.setOnClickListener {
            viewModel.dispatch(HomeContract.Intent.NavigateToSearch)
        }
    }

    private fun setupRecyclerView() = with(binding.mainRecyclerView) {
        val gridLayoutManager = GridLayoutManager(context, GRID_SPAN)
        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {

                val mod = position and 3
                val viewType = mainAdapter.getItemViewType(position)

                return when {
                    viewType == MainBottomAdapter.ITEM_DELIVERY -> 2
                    viewType == MainBottomAdapter.ITEM_EXPRESS -> 3
                    viewType == MainBottomAdapter.ITEM_INTEREST -> 5
                    mod in listOf(0, 3) -> 3
                    else -> 2
                }
            }
        }

        layoutManager = gridLayoutManager
        adapter = mainAdapter

        addItemDecoration(
            GridSpacingItemDecoration(
                spanCount = 2,
                spacing = context.resources.getDimension(R.dimen.bottom_sheet_corner_radius).toInt(),
                includeEdge = true
            )
        )
    }

    private fun handleSideEffect(effect: HomeContract.SideEffect) {

    }

    override fun onDestroyView() {
        binding.mainRecyclerView.adapter = null
        super.onDestroyView()
    }

    private companion object {
        const val GRID_SPAN = 5
    }

    override fun onBackPressed(): Boolean = viewModel.onBackPressed()
}