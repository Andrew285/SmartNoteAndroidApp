package com.example.smartnoteapp.main.presentation

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.example.smartnoteapp.R
import com.example.smartnoteapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0)
            insets
        }

        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.item_home -> {
                    findNavController(R.id.fragmentMainContainerView).navigate(R.id.homeFragment)
                    true
                }
                R.id.item_search -> {
                    findNavController(R.id.fragmentMainContainerView).navigate(R.id.searchFragment)
                    true
                }
                R.id.item_favourites -> {
                    findNavController(R.id.fragmentMainContainerView).navigate(R.id.favouritesFragment)
                    true
                }
                R.id.item_profile -> {
                    findNavController(R.id.fragmentMainContainerView).navigate(R.id.profileFragment)
                    true
                }
                else -> false
            }
        }
    }
}