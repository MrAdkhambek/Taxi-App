package me.adkhambek.taxi.di.vh

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.multibindings.IntKey
import dagger.multibindings.IntoMap
import me.adkhambek.taxi.databinding.ItemDeliveryBinding
import me.adkhambek.taxi.databinding.ItemExpressBinding
import me.adkhambek.taxi.databinding.ItemHistoryBinding
import me.adkhambek.taxi.databinding.ItemInterestBinding
import me.adkhambek.taxi.databinding.ItemLoadingBinding
import me.adkhambek.taxi.datasource.paging.InterestMediator
import me.adkhambek.taxi.ui.bottom.home.adapter.main.DeliveryViewHolder
import me.adkhambek.taxi.ui.bottom.home.adapter.main.ExpressViewHolder
import me.adkhambek.taxi.ui.bottom.home.adapter.main.HistoryViewHolder
import me.adkhambek.taxi.ui.bottom.home.adapter.main.InterestViewHolder
import me.adkhambek.taxi.ui.bottom.home.adapter.main.LoadingViewHolder
import me.adkhambek.taxi.ui.bottom.home.adapter.main.MainBottomAdapter
import me.adkhambek.taxi.utils.recycler.ViewHolderFactory
import javax.inject.Provider


@Module
@InstallIn(FragmentComponent::class)
object MainViewHoldersModule {

    @[Provides IntoMap IntKey(MainBottomAdapter.ITEM_DELIVERY)]
    fun bindDeliveryViewHolder(): ViewHolderFactory = ViewHolderFactory { itemView ->
        DeliveryViewHolder(ItemDeliveryBinding.bind(itemView))
    }

    @[Provides IntoMap IntKey(MainBottomAdapter.ITEM_HISTORY)]
    fun bindHistoryViewHolder(): ViewHolderFactory = ViewHolderFactory { itemView ->
        HistoryViewHolder(ItemHistoryBinding.bind(itemView))
    }

    @[Provides IntoMap IntKey(MainBottomAdapter.ITEM_EXPRESS)]
    fun bindExpressViewHolder(): ViewHolderFactory = ViewHolderFactory { itemView ->
        ExpressViewHolder(ItemExpressBinding.bind(itemView))
    }

    @[Provides IntoMap IntKey(MainBottomAdapter.ITEM_LOADING)]
    fun bindLoadingViewHolder(): ViewHolderFactory = ViewHolderFactory { itemView ->
        LoadingViewHolder(ItemLoadingBinding.bind(itemView))
    }

    @[Provides IntoMap IntKey(MainBottomAdapter.ITEM_INTEREST)]
    fun bindInterestViewHolder(
        interestMediator: Provider<InterestMediator>,
    ): ViewHolderFactory = ViewHolderFactory { itemView ->
        InterestViewHolder(
            interestMediator = interestMediator,
            binding = ItemInterestBinding.bind(itemView),
        )
    }
}