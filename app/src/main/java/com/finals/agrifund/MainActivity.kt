package com.finals.agrifund

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.finals.agrifund.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navController = findNavController(R.id.host_main_fragment)
        binding.bottomNavBar.setupWithNavController(navController)
        val userName = intent.getStringExtra("USER_NAME")
        val userEmail = intent.getStringExtra("USER_EMAIL")
        val userPhoto = intent.getStringExtra("USER_PHOTO")

        val profileFragment = Profile().apply {
            arguments = Bundle().apply {
                putString("USER_NAME", userName)
                putString("USER_EMAIL", userEmail)
                putString("USER_PHOTO", userPhoto)

            }
        }
    }
}
