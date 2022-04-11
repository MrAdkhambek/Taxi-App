package me.adkhambek.taxi.ui.bottom.home.adapter.main

import android.view.LayoutInflater
import android.view.ViewGroup
import me.adkhambek.taxi.R
import me.adkhambek.taxi.datasource.models.BaseMainItem
import me.adkhambek.taxi.datasource.models.Delivery
import me.adkhambek.taxi.datasource.models.Express
import me.adkhambek.taxi.datasource.models.History
import me.adkhambek.taxi.datasource.models.Interesting
import me.adkhambek.taxi.utils.recycler.LifecycleListAdapter
import me.adkhambek.taxi.utils.recycler.ViewHolderFactory
import javax.inject.Inject
import javax.inject.Provider


class MainBottomAdapter @Inject constructor(
    private val viewHolderFactories: Map<Int, @JvmSuppressWildcards Provider<ViewHolderFactory>>
) : LifecycleListAdapter<BaseMainItem, BaseViewHolder>(BaseDiff.BaseItemDiff) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(viewType, parent, false)

        return viewHolderFactories[viewType]?.get()?.create(view) ?: throw IllegalArgumentException("Wrong item type")
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        val item = getItem(position)

        when {
            holder is LoadingViewHolder -> holder.onBind()
            holder is ExpressViewHolder && item is Express -> holder.onBind()
            holder is DeliveryViewHolder && item is Delivery -> holder.onBind()
            holder is HistoryViewHolder && item is History -> holder.onBind(item)
            holder is InterestViewHolder && item is Interesting -> holder.onBind()
        }
    }

    override fun getItemViewType(position: Int): Int = when (getItem(position)) {
        is History -> ITEM_HISTORY
        is Express -> ITEM_EXPRESS
        is Delivery -> ITEM_DELIVERY
        is Interesting -> ITEM_INTEREST
        else -> ITEM_LOADING
    }

    override fun onViewAttachedToWindow(holder: BaseViewHolder) {
        super.onViewAttachedToWindow(holder)
        holder.onAttached()
    }

    companion object {
        const val ITEM_HISTORY = R.layout.item_history
        const val ITEM_EXPRESS = R.layout.item_express
        const val ITEM_LOADING = R.layout.item_loading
        const val ITEM_DELIVERY = R.layout.item_delivery
        const val ITEM_INTEREST = R.layout.item_interest
    }
}