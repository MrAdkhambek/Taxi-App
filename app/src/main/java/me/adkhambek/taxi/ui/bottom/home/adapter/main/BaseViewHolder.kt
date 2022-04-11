package me.adkhambek.taxi.ui.bottom.home.adapter.main

import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import me.adkhambek.taxi.databinding.ItemDeliveryBinding
import me.adkhambek.taxi.databinding.ItemExpressBinding
import me.adkhambek.taxi.databinding.ItemHistoryBinding
import me.adkhambek.taxi.databinding.ItemInterestBinding
import me.adkhambek.taxi.databinding.ItemLoadingBinding
import me.adkhambek.taxi.datasource.models.History
import me.adkhambek.taxi.datasource.models.InterestSubItem
import me.adkhambek.taxi.datasource.paging.InterestMediator
import me.adkhambek.taxi.ui.bottom.home.adapter.interest.InterestAdapter
import me.adkhambek.taxi.utils.recycler.LifecycleViewHolder
import javax.inject.Provider


abstract class BaseViewHolder(
    view: View
) : LifecycleViewHolder(view)


class HistoryViewHolder(
    private val binding: ItemHistoryBinding,
) : BaseViewHolder(binding.root) {

    fun onBind(item: History) = with(binding) {
        timeTextView.text = item.time
        titleTextView.text = item.title
    }
}


class ExpressViewHolder(
    private val binding: ItemExpressBinding,
) : BaseViewHolder(binding.root) {


    fun onBind() = Unit
}


class InterestViewHolder(
    private val binding: ItemInterestBinding,
    private val interestMediator: Provider<InterestMediator>,
) : BaseViewHolder(binding.root) {

    private val baseItems: Flow<PagingData<InterestSubItem>> = Pager(
        config = PagingConfig(pageSize = 20, enablePlaceholders = true),
        pagingSourceFactory = interestMediator::get
    ).flow.cachedIn(lifecycleScope)

    fun onBind() {
        val interestAdapter = InterestAdapter()

        lifecycleScope.launch {
            baseItems.collectLatest(interestAdapter::submitData)
        }

        with(binding.recyclerView) {
            layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
            this.adapter = interestAdapter
        }
    }

    override fun onDetached() {
        binding.recyclerView.adapter = null
        super.onDetached()
    }
}


class DeliveryViewHolder(
    private val binding: ItemDeliveryBinding,
) : BaseViewHolder(binding.root) {


    fun onBind() = Unit
}


class LoadingViewHolder(
    private val binding: ItemLoadingBinding,
) : BaseViewHolder(binding.root) {

    fun onBind() = Unit
}


