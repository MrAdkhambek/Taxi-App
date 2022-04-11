package me.adkhambek.taxi.ui.bottom.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import me.adkhambek.taxi.R
import me.adkhambek.taxi.databinding.FragmentSearchAddressBinding
import me.adkhambek.taxi.datasource.models.AddressModel
import me.adkhambek.taxi.ktx.screenHeight
import me.adkhambek.taxi.ktx.screenWidth
import me.adkhambek.taxi.ktx.showKeyboard
import me.adkhambek.taxi.ui.bottom.search.SearchAddressContract.Intent.SearchAddressIntent
import me.adkhambek.taxi.ui.bottom.search.SearchAddressContract.State
import me.adkhambek.taxi.ui.bottom.search.SearchAddressContract.ViewModel
import me.adkhambek.taxi.ui.bottom.search.adapter.SearchAddressAdapter
import me.adkhambek.taxi.utils.BackButtonListener
import me.adkhambek.taxi.utils.diffing.diff
import me.adkhambek.taxi.utils.toCharSequence
import org.orbitmvi.orbit.viewmodel.observe
import java.util.concurrent.TimeUnit


@AndroidEntryPoint
class SearchAddressFragment :
    Fragment(R.layout.fragment_search_address),
    BackButtonListener,
    SearchAddressAdapter.Listener {

    private val binding by viewBinding(FragmentSearchAddressBinding::bind)
    private val viewModel: ViewModel by viewModels<SearchAddressViewModel>()

    private lateinit var searchAddressAdapter: SearchAddressAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return super.onCreateView(inflater, container, savedInstanceState)?.apply {
            layoutParams = ViewGroup.LayoutParams(
                screenWidth, screenHeight
            )
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupIntents()
        setupRecyclerView()

        val renderer = diff<State> {
            diff(get = State::listOfAddress, set = searchAddressAdapter::submitList)
        }

        viewModel
            .observe(
                state = renderer::render,
                sideEffect = this::handleSideEffect,
                lifecycleOwner = viewLifecycleOwner,
            )
    }

    private fun setupRecyclerView() = with(binding.mainRecyclerView) {
        searchAddressAdapter = SearchAddressAdapter(this@SearchAddressFragment)
        layoutManager = LinearLayoutManager(context)
        adapter = searchAddressAdapter
    }

    private fun setupIntents() = with(binding) {
        dynamicLayout.addressEditText.doAfterTextChanged {
            viewModel.dispatch(SearchAddressIntent(it.toString()))
        }

        root.postDelayed({
            dynamicLayout.addressEditText.showKeyboard()
        }, TimeUnit.SECONDS.toMillis(1))
    }

    private fun handleSideEffect(effect: SearchAddressContract.SideEffect): Unit = when (effect) {
        is SearchAddressContract.SideEffect.Toast ->
            Toast.makeText(requireContext(), effect.message.toCharSequence(requireContext()), Toast.LENGTH_SHORT).show()
    }

    override fun onClickAddress(item: AddressModel?) {
        item ?: return
        viewModel.dispatch(SearchAddressContract.Intent.NavigateToOrder(item))
    }

    override fun onDestroyView() {
        binding.mainRecyclerView.adapter = null
        super.onDestroyView()
    }

    override fun onBackPressed(): Boolean = viewModel.onBackPressed()
}