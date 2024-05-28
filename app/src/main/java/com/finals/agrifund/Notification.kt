package com.finals.agrifund

import android.os.Bundle
import android.widget.ImageView
import android.widget.Switch
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Notification : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_notification)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val notificationSwitch: Switch = findViewById(R.id.switch_notification)
        val bellImageView: ImageView = findViewById(R.id.bellImageView)

        notificationSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                // Bell is on
                bellImageView.setImageResource(R.drawable.bell_on)
                Toast.makeText(this, "Notification Enabled", Toast.LENGTH_SHORT).show()
            } else {
                // Bell is off
                bellImageView.setImageResource(R.drawable.bell_off)
                Toast.makeText(this, "Notification Disabled", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
