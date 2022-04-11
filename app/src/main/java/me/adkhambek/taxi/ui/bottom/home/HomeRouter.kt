package me.adkhambek.taxi.ui.bottom.home

import com.github.terrakok.cicerone.Router
import me.adkhambek.taxi.ui.bottom.BottomScreens
import javax.inject.Inject


class HomeRouter @Inject constructor(
    private val router: Router
) : HomeContract.Router {

    override suspend fun navigateSearch() {
        router.navigateTo(BottomScreens.Search())
    }
}