package me.adkhambek.taxi.ui

import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.commitNow
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.github.terrakok.cicerone.Command
import com.github.terrakok.cicerone.Navigator
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Replace
import com.github.terrakok.cicerone.androidx.AppNavigator
import com.github.terrakok.cicerone.androidx.FragmentScreen
import com.google.android.material.bottomsheet.BottomSheetBehavior
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import me.adkhambek.taxi.R
import me.adkhambek.taxi.databinding.ActivityMapsBinding
import me.adkhambek.taxi.map.MapFragment
import me.adkhambek.taxi.ui.MainContract.Intent.ChangeBottomOffset
import me.adkhambek.taxi.ui.MainContract.Intent.ShowFavoriteAddress
import me.adkhambek.taxi.ui.MainContract.State
import me.adkhambek.taxi.ui.MainContract.ViewModel
import me.adkhambek.taxi.ui.bottom.BottomScreens
import me.adkhambek.taxi.ui.bottom.BottomSheetState
import me.adkhambek.taxi.ui.bottom.favorite.FavoriteAddressFragment
import me.adkhambek.taxi.utils.BackButtonListener
import me.adkhambek.taxi.utils.BottomSheetSharedTransition
import me.adkhambek.taxi.utils.diffing.diff
import org.orbitmvi.orbit.viewmodel.observe
import javax.inject.Inject
import javax.inject.Provider


@AndroidEntryPoint
class MainActivity : AppCompatActivity(R.layout.activity_maps) {

    private val viewModel: ViewModel by viewModels<MainViewModel>()
    private val binding: ActivityMapsBinding by viewBinding(ActivityMapsBinding::bind, R.id.main_container)

    @Inject lateinit var navigatorHolder: NavigatorHolder
    @Inject lateinit var mapFragmentProvider: Provider<MapFragment>

    private val navigator: Navigator = object : AppNavigator(this, R.id.bottom_container) {
        override fun setupFragmentTransaction(
            screen: FragmentScreen,
            fragmentTransaction: FragmentTransaction,
            currentFragment: Fragment?,
            nextFragment: Fragment,
        ) {

            currentFragment?.view?.let { currentFragmentRoot ->
                fragmentTransaction.addSharedElement(currentFragmentRoot, currentFragmentRoot.transitionName)
                fragmentTransaction.setReorderingAllowed(true)
            }

            nextFragment.sharedElementEnterTransition = BottomSheetSharedTransition()
            super.setupFragmentTransaction(screen, fragmentTransaction, currentFragment, nextFragment)
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
            supportFragmentManager
                .commitNow {
                    val fragment = mapFragmentProvider.get()
                    replace(R.id.map_container, fragment, MapFragment::class.qualifiedName)
                }

            navigator.applyCommands(arrayOf<Command>(Replace(BottomScreens.Home())))
        }

        setupIntents()
        val sheetBehavior = setupBottomSheet()
        val bottomSheetLineDelta = resources.getDimension(R.dimen.bottom_sheet_line_delta)

        val renderer = diff<State> {
            diff(get = State::slideOffset) { slideOffset ->
                binding.bottomSheetContainer.bottomSheetLine.translationY = bottomSheetLineDelta * slideOffset
            }

            diff(get = State::bottomSheetState) { state ->
                when (state) {
                    BottomSheetState.MainState -> {
                        binding.favoriteFab.hide()
                        binding.locationFab.show()
                        binding.backFab.hide()
                        binding.menuFab.show()

                        lifecycleScope.launch {
                            sheetBehavior.isFitToContents = false
                            sheetBehavior.halfExpandedRatio = 0.25f
                            sheetBehavior.state = BottomSheetBehavior.STATE_HALF_EXPANDED
//                            delay(3000)
//                            sheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
                        }
                    }
                    BottomSheetState.SearchState -> {
                        binding.favoriteFab.show()
                        binding.locationFab.hide()
                        binding.backFab.hide()
                        binding.menuFab.hide()

                        sheetBehavior.isFitToContents = false
                        sheetBehavior.halfExpandedRatio = 0.99f
                        sheetBehavior.state = BottomSheetBehavior.STATE_HALF_EXPANDED
                    }
                    BottomSheetState.StopPointState -> {
                        binding.favoriteFab.hide()
                        binding.locationFab.hide()
                        binding.backFab.show()
                        binding.menuFab.hide()

                        sheetBehavior.isFitToContents = true
                    }
                }
            }
        }

        viewModel
            .observe(
                lifecycleOwner = this,
                state = renderer::render,
                sideEffect = this::handleSideEffect,
            )
    }

    private fun setupIntents() = with(binding) {
        favoriteFab.setOnClickListener {
            viewModel.dispatch(ShowFavoriteAddress)
        }

        backFab.setOnClickListener {
            viewModel.onBackPressed()
        }
    }

    private fun handleSideEffect(effect: MainContract.SideEffect): Unit = when (effect) {
        MainContract.SideEffect.ShowFavoriteAddress -> {
            FavoriteAddressFragment()
                .show(supportFragmentManager, FavoriteAddressFragment::class.simpleName)
        }
    }

    private fun setupBottomSheet(): BottomSheetBehavior<FrameLayout> {
        val bottomSheetView = binding.bottomSheetContainer.root
        val sheetBehavior = BottomSheetBehavior.from(bottomSheetView)

        sheetBehavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) = Unit
            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                viewModel.dispatch(ChangeBottomOffset(slideOffset))
            }
        })

        return sheetBehavior
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        navigatorHolder.removeNavigator()
        super.onPause()
    }

    private val currentFragment: Fragment?
        get() = supportFragmentManager.fragments.lastOrNull { !it.isHidden }

    override fun onBackPressed() {
        val currentBackButtonListener = (currentFragment as? BackButtonListener?) ?: return super.onBackPressed()
        if (!currentBackButtonListener.onBackPressed()) return super.onBackPressed()
    }
}