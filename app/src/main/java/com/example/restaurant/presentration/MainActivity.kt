package com.example.restaurant.presentration

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup.MarginLayoutParams
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.restaurant.R
import com.example.restaurant.databinding.ActivityMainBinding
import com.example.restaurant.presentration.map.UserLocationActivity
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

        val frameLayout = findViewById<View>(R.id.nav_host_fragment_activity_main) as FrameLayout


        val bottomNavBar:  BottomNavBar.OnBottomNavigationListener  =
            BottomNavBar.OnBottomNavigationListener {
                when (it.getItemId()) {
                    R.id.restaurant_data -> {
                        binding.mapLocation.visibility = View.VISIBLE
                        navController.navigate(R.id.shop_list_fragment)
                        setMargins(frameLayout,0,80,0,0)

                    }
                    R.id.restaurant_search_data ->{
                        binding.mapLocation.visibility = View.VISIBLE
                        navController.navigate(R.id.restaurant_list_search)
                        setMargins(frameLayout,0,80,0,0)
                    }
                    R.id.account ->{
                        binding.mapLocation.visibility = View.GONE
                        navController.navigate(R.id.account)
                        setMargins(frameLayout,0,0,0,0)
                    }

                }
                NavigationUI.onNavDestinationSelected(it, navController)
                return@OnBottomNavigationListener true
            }

        val bottomNavView: BottomNavBar = findViewById(R.id.bottom_nav_view)
        bottomNavView.setBottomNavigationListener(bottomNavBar)


        navController.addOnDestinationChangedListener { _, destination, _ ->
            if(destination.id == R.id.shopDetailFragment) {

                bottomNavView.visibility = View.GONE
                binding.mapLocation.visibility = View.GONE
                setMargins(frameLayout,0,0,0,0)
            }
           else if (destination.id == R.id.account){
                bottomNavView.visibility = View.VISIBLE
                binding.mapLocation.visibility = View.GONE
                setMargins(frameLayout,0,0,0,0)
            }
           else{
                bottomNavView.visibility = View.VISIBLE
                binding.mapLocation.visibility = View.VISIBLE
                setMargins(frameLayout,0,80,0,0)
            }
        }

        binding.mapLocation.setOnClickListener {
            startActivity(Intent(this,UserLocationActivity::class.java))
        }

    }

    private fun setMargins(view: View, left: Int, top: Int, right: Int, bottom: Int) {
        if (view.layoutParams is MarginLayoutParams) {
            val p = view.layoutParams as MarginLayoutParams
            p.setMargins(left, top, right, bottom)
            view.requestLayout()
        }
    }
}
