package me.adkhambek.taxi.ui.bottom.stop.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import me.adkhambek.taxi.databinding.ItemTariffBinding
import me.adkhambek.taxi.datasource.models.Tariff


internal class TariffAdapter : ListAdapter<Tariff, TariffViewHolder>(DIFF) {

    private companion object DIFF : DiffUtil.ItemCallback<Tariff>() {
        override fun areItemsTheSame(oldItem: Tariff, newItem: Tariff): Boolean = oldItem.title == newItem.title
        override fun areContentsTheSame(oldItem: Tariff, newItem: Tariff): Boolean = oldItem == newItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TariffViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return TariffViewHolder(ItemTariffBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: TariffViewHolder, position: Int) {
        val item = getItem(position)

        with(holder.binding) {
            titleTextView.text = item.title
            subtitleTextView.text = item.subtitle
        }
    }

}

