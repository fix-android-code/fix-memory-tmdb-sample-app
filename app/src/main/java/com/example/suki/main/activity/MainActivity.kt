package com.example.suki.main.activity

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.suki.R
import com.example.suki.common.util.extension.addSystemWindowInsetToPadding
import com.example.suki.databinding.ActivityMainBinding
import com.example.suki.main.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel: MainViewModel by viewModels()

    private lateinit var navHostFragment: NavHostFragment

    private lateinit var navController: NavController

    val root get() = binding.root

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setup()
    }

    private fun setup() {
        setBottomNavigationView()
    }

    private fun setBottomNavigationView() {
        val destinationId = R.id.movie_navigation

        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.navHostFragmentContainerView) as NavHostFragment

        navController = navHostFragment.navController

        binding.bottomNavigationView.selectedItemId = destinationId

        binding.bottomNavigationView.menu.findItem(destinationId)?.isChecked = true

        val graph = navController.navInflater.inflate(R.navigation.nav_main_graph)

        graph.setStartDestination(destinationId)

        navController.graph = graph

        binding.bottomNavigationView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                else -> {
                    showBottomNavigationView()
                    showToolbar()
                }
            }
        }

        binding.navHostFragmentContainerView.addSystemWindowInsetToPadding(bottom = true)
    }

    fun navigate(
        @IdRes idAction: Int,
        bundle: Bundle? = null,
        sharedView: View? = null
    ) {
        if (sharedView != null) {
            val extras = FragmentNavigatorExtras(sharedView to sharedView.transitionName)
            navController.navigate(idAction, bundle, null, extras)
        } else {
            navController.navigate(idAction, bundle, null, null)
        }
    }

    private fun showBottomNavigationView(show: Boolean = true) {
        binding.bottomNavigationView.visibility = if (show) View.VISIBLE else View.INVISIBLE
    }

    private fun showToolbar(show: Boolean = true) {
//        binding.appBarLayout.root.setExpanded(show)
    }
}