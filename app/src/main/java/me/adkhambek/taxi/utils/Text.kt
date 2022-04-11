package me.adkhambek.taxi.utils

import android.content.Context
import android.widget.TextView
import androidx.annotation.StringRes


sealed interface Text {
    data class PlainText(val value: String) : Text
    data class ResText(@StringRes val resId: Int, val formatArgs: List<Any>? = null) : Text
}

fun Text.toCharSequence(context: Context): CharSequence {
    return when (this) {
        is Text.PlainText -> this.value
        is Text.ResText -> if (formatArgs == null) context.getString(resId)
        else context.getString(resId, *formatArgs.toTypedArray())
    }
}

fun TextView.setText(text: Text) {
    this.text = text.toCharSequence(context)
}