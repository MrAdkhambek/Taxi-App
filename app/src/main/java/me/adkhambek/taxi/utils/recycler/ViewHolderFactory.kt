package me.adkhambek.taxi.utils.recycler

import android.view.View
import me.adkhambek.taxi.ui.bottom.home.adapter.main.BaseViewHolder


fun interface ViewHolderFactory {
    fun create(view: View): BaseViewHolder
}