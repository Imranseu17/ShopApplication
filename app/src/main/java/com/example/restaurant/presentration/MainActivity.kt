package com.example.restaurant.presentration

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.example.restaurant.R
import com.example.restaurant.databinding.ActivityMainBinding
import com.example.restaurant.usecase.BottomNavBar
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navController = findNavController(R.id.nav_host_fragment_activity_main)

        val bottomNavBar:  BottomNavBar.OnBottomNavigationListener  =
            BottomNavBar.OnBottomNavigationListener {
                when (it.getItemId()) {
                    R.id.restaurant_data -> navController.navigate(R.id.shop_list_fragment)
                    R.id.restaurant_search_data -> navController.navigate(R.id.restaurant_list_search)
                    R.id.map_location -> navController.navigate(R.id.map_location)

                }
                NavigationUI.onNavDestinationSelected(it, navController)
                return@OnBottomNavigationListener true
            }

        val bottomNavView: BottomNavBar = findViewById(R.id.bottom_nav_view)
        bottomNavView.setBottomNavigationListener(bottomNavBar)


        navController.addOnDestinationChangedListener { _, destination, _ ->
            if(destination.id == R.id.shopDetailFragment) {

                bottomNavView.visibility = View.GONE
            } else {

                bottomNavView.visibility = View.VISIBLE
            }
        }




    }
}
