package me.adkhambek.taxi.ui.bottom.home.adapter.main

import androidx.recyclerview.widget.DiffUtil
import me.adkhambek.taxi.datasource.models.BaseMainItem
import me.adkhambek.taxi.datasource.models.Delivery
import me.adkhambek.taxi.datasource.models.Express
import me.adkhambek.taxi.datasource.models.History
import me.adkhambek.taxi.datasource.models.Interesting

object BaseDiff {

    object BaseItemDiff : DiffUtil.ItemCallback<BaseMainItem>() {

        override fun areItemsTheSame(oldItem: BaseMainItem, newItem: BaseMainItem): Boolean = when {
            oldItem is History && newItem is History -> HistoryDiff.areItemsTheSame(oldItem, newItem)
            oldItem is Express && newItem is Express -> ExpressDiff.areItemsTheSame(oldItem, newItem)
            oldItem is Delivery && newItem is Delivery -> DeliveryDiff.areItemsTheSame(oldItem, newItem)
            oldItem is Interesting && newItem is Interesting -> InterestingDiff.areItemsTheSame(oldItem, newItem)
            else -> false
        }

        override fun areContentsTheSame(oldItem: BaseMainItem, newItem: BaseMainItem): Boolean = when {
            oldItem is History && newItem is History -> HistoryDiff.areContentsTheSame(oldItem, newItem)
            oldItem is Express && newItem is Express -> ExpressDiff.areContentsTheSame(oldItem, newItem)
            oldItem is Delivery && newItem is Delivery -> DeliveryDiff.areContentsTheSame(oldItem, newItem)
            oldItem is Interesting && newItem is Interesting -> InterestingDiff.areContentsTheSame(oldItem, newItem)
            else -> false
        }
    }

    object HistoryDiff : DiffUtil.ItemCallback<History>() {
        override fun areItemsTheSame(oldItem: History, newItem: History): Boolean = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: History, newItem: History): Boolean = oldItem.title == newItem.title
    }

    object DeliveryDiff : DiffUtil.ItemCallback<Delivery>() {
        override fun areItemsTheSame(oldItem: Delivery, newItem: Delivery): Boolean = oldItem === newItem
        override fun areContentsTheSame(oldItem: Delivery, newItem: Delivery): Boolean = oldItem.equals(newItem)
    }

    object ExpressDiff : DiffUtil.ItemCallback<Express>() {
        override fun areItemsTheSame(oldItem: Express, newItem: Express): Boolean = oldItem === newItem
        override fun areContentsTheSame(oldItem: Express, newItem: Express): Boolean = oldItem.equals(newItem)
    }

    object InterestingDiff : DiffUtil.ItemCallback<Interesting>() {
        override fun areItemsTheSame(oldItem: Interesting, newItem: Interesting): Boolean = oldItem === newItem
        override fun areContentsTheSame(oldItem: Interesting, newItem: Interesting): Boolean = oldItem.equals(newItem)
    }
}