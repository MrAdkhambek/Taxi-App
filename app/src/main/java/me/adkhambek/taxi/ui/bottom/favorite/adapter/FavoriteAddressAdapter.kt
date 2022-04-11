package me.adkhambek.taxi.ui.bottom.favorite.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import me.adkhambek.taxi.databinding.ItemFavoriteAddressBinding
import me.adkhambek.taxi.datasource.models.AddressModel


class FavoriteAddressAdapter(
    private val listener: Listener,
) : ListAdapter<AddressModel, FavoriteAddressViewHolder>(DIFF) {

    private companion object DIFF : DiffUtil.ItemCallback<AddressModel>() {
        override fun areItemsTheSame(oldItem: AddressModel, newItem: AddressModel): Boolean = oldItem.address == newItem.address
        override fun areContentsTheSame(oldItem: AddressModel, newItem: AddressModel): Boolean = oldItem == newItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteAddressViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val vh = FavoriteAddressViewHolder(ItemFavoriteAddressBinding.inflate(inflater, parent, false))
        vh.itemView.setOnClickListener {
            val item = getItem(vh.absoluteAdapterPosition)
            listener.onClickAddress(item)
        }

        return vh
    }

    override fun onBindViewHolder(holder: FavoriteAddressViewHolder, position: Int) {
        val item = getItem(position)

        with(holder.binding) {
            titleTextView.text = item.address
            subtitleTextView.text = item.formattedAddress
        }
    }


    fun interface Listener {
        fun onClickAddress(item: AddressModel?)
    }
}

class FavoriteAddressViewHolder(
    val binding: ItemFavoriteAddressBinding,
) : RecyclerView.ViewHolder(binding.root)