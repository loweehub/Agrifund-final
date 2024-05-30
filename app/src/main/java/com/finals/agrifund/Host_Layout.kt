package com.finals.agrifund

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.finals.agrifund.databinding.ActivityMainBinding

class Host_Layout : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Enable edge-to-edge display
        enableEdgeToEdge()

        // Set up navigation controller
        val navController = findNavController(R.id.fragment_container)

        // Set up bottom navigation view with navigation controller
        binding.bottomNavBar.setupWithNavController(navController)

        // Replace the content of the activity with the fragment
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, Campaign_Dashboard())
            .commit()
    }
}
