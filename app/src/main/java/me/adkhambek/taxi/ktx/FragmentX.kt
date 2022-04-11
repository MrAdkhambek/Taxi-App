package me.adkhambek.taxi.ktx

import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.lifecycleScope


inline val Fragment.screenWidth: Int
    get() = resources.displayMetrics.widthPixels

inline val Fragment.screenHeight: Int
    get() = resources.displayMetrics.heightPixels

inline val Fragment.viewLifecycleScope: LifecycleCoroutineScope
    get() = viewLifecycleOwner.lifecycleScope
