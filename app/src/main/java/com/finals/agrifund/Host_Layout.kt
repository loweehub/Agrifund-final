package com.finals.agrifund

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Host_Layout : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_host_layout)


        val message = intent.getStringExtra("message")

        // Replace the content of the activity with the fragment
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, Campaign_Dashboard())
            .commit()

    }
}