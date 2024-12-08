package com.example.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView

class AdminActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)

        // NavHostFragment ve NavController bağlantısını yap
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.adminNavHostFragment) as NavHostFragment
        navController = navHostFragment.navController

        // BottomNavigationView ile NavController'ı bağla
        val bottomNavigationView =
            findViewById<BottomNavigationView>(R.id.adminBottomNavigation)

        // Otomatik olarak BottomNavigationView ile NavController'ı bağlayın
        NavigationUI.setupWithNavController(bottomNavigationView, navController)
    }
}