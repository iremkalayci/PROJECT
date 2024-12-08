package com.example.myapplication

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapplication.databinding.ActivityMainBinding
import android.view.MenuItem
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.Navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // findViewById ile bottomNavigationView'yi bulma
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)

        // NavController ile bağlama
        val navController = findNavController(this, R.id.fragment)
        bottomNavigationView.setupWithNavController(navController)
    }
}