package me.adkhambek.taxi.utils.diffing

import androidx.annotation.MainThread


interface ViewRenderer<in Model : Any> {

    /**
     * Renders (displays) the provided `View Model`
     *
     * @param model a `View Model` to be rendered (displayed)
     */
    @MainThread
    fun render(model: Model)
}