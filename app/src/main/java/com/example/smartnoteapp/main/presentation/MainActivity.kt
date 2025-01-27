package com.example.smartnoteapp.main.presentation

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.example.smartnoteapp.R
import com.example.smartnoteapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
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
                R.id.item_videos -> {
                    findNavController(R.id.fragmentMainContainerView).navigate(R.id.videosFragment)
                    true
                }
                R.id.item_create_note -> {
                    findNavController(R.id.fragmentMainContainerView).navigate(R.id.createNoteFragment)
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

    override fun onStart() {
        super.onStart()

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 1)
        }
    }
}