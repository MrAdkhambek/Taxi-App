package me.adkhambek.taxi.utils.recycler

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView


class HorizontalSpacingItemDecorator constructor(
    private val top: Int,
    private val bottom: Int,
    private val left: Int,
    private val right: Int
) : RecyclerView.ItemDecoration() {

    constructor(padding: Float) : this(padding.toInt())
    constructor(padding: Int) : this(padding, padding, padding, padding)

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent
            .getChildAdapterPosition(view)
            .let { if (it == RecyclerView.NO_POSITION) return else it }

        val right = parent.adapter?.let { adapter ->
            if (position == adapter.itemCount - 1) this.right * 2 else this.right
        } ?: this.right

        val left = if (position == 0) this.left * 2 else this.left
        outRect.set(left, top, right, bottom)
    }
}