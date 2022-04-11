package me.adkhambek.taxi.ui.bottom.home.adapter.interest

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import me.adkhambek.taxi.databinding.SubItemInterestBinding
import me.adkhambek.taxi.datasource.models.InterestSubItem


class InterestAdapter : PagingDataAdapter<InterestSubItem, InterestSubViewHolder>(DIFF) {

    private companion object DIFF : DiffUtil.ItemCallback<InterestSubItem>() {
        override fun areItemsTheSame(oldItem: InterestSubItem, newItem: InterestSubItem): Boolean = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: InterestSubItem, newItem: InterestSubItem): Boolean = oldItem.id == newItem.id
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InterestSubViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return InterestSubViewHolder(SubItemInterestBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: InterestSubViewHolder, position: Int) {
        val item = getItem(position) ?: return
        with(holder.binding) {
            imageView.load(item.photo)
        }
    }
}


class InterestSubViewHolder(
    val binding: SubItemInterestBinding
) : RecyclerView.ViewHolder(binding.root)