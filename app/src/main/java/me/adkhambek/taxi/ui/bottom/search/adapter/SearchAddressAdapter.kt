package me.adkhambek.taxi.ui.bottom.search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import me.adkhambek.taxi.R
import me.adkhambek.taxi.databinding.ItemAddressBinding
import me.adkhambek.taxi.datasource.models.AddressModel


class SearchAddressAdapter(
    private val listener: Listener,
) : ListAdapter<AddressModel, AddressViewHolder>(DIFF) {

    private companion object DIFF : DiffUtil.ItemCallback<AddressModel>() {
        override fun areItemsTheSame(oldItem: AddressModel, newItem: AddressModel): Boolean = oldItem.address == newItem.address
        override fun areContentsTheSame(oldItem: AddressModel, newItem: AddressModel): Boolean = oldItem == newItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddressViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val vh = AddressViewHolder(ItemAddressBinding.inflate(inflater, parent, false))
        vh.itemView.setOnClickListener {
            val item = getItem(vh.absoluteAdapterPosition)
            listener.onClickAddress(item)
        }
        return vh
    }

    override fun onBindViewHolder(holder: AddressViewHolder, position: Int) {
        val item = getItem(position)

        with(holder.binding) {
            titleTextView.text = item.address
            subtitleTextView.text = item.formattedAddress

            distanceTextView.apply {
                isVisible = item.distance != null
                text = context.getString(R.string.distance_hint, item.distance)
            }
        }
    }

    fun interface Listener {
        fun onClickAddress(item: AddressModel?)
    }
}

class AddressViewHolder(
    val binding: ItemAddressBinding,
) : RecyclerView.ViewHolder(binding.root)