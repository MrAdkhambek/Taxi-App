package me.adkhambek.taxi.datasource.models


data class History(
    val id: Long,
    val title: String,
    val time: String,
) : BaseMainItem


object Express : BaseMainItem
object Delivery : BaseMainItem
object Interesting : BaseMainItem