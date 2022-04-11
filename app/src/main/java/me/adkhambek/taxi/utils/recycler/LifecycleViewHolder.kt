package me.adkhambek.taxi.utils.recycler

import android.view.View
import androidx.annotation.CallSuper
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.recyclerview.widget.RecyclerView


abstract class LifecycleViewHolder(
    itemView: View
) : RecyclerView.ViewHolder(itemView), LifecycleOwner {

    private var lifecycleRegistry: LifecycleRegistry
    override fun getLifecycle(): Lifecycle = lifecycleRegistry

    init {
        lifecycleRegistry = createLifeCycle()
    }

    private fun createLifeCycle(): LifecycleRegistry {
        val lifecycle = LifecycleRegistry(this)
        lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_CREATE)
        return lifecycle
    }

    @CallSuper
    open fun onAttached() {
        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_START)
    }

    @CallSuper
    open fun onDetached() {
        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    }

    @CallSuper
    open fun onRecycled() {
        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY)
        lifecycleRegistry = createLifeCycle()
    }
}
