package com.finals.agrifund

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.cardview.widget.CardView
import com.bumptech.glide.Glide
import com.finals.agrifund.user.LoginActivity

class Profile : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profile, container, false)


        // Retrieve user details from arguments
        val userName = arguments?.getString("USER_NAME")
        val userEmail = arguments?.getString("USER_EMAIL")
        val userPhoto = arguments?.getString("USER_PHOTO")

        // Update UI with user details
        val profilePicture = view.findViewById<ImageView>(R.id.profile_picture)
        val profileName = view.findViewById<TextView>(R.id.profile_name)
        val profileEmail = view.findViewById<TextView>(R.id.profile_email)

        // Load user photo using Glide or any other image loading library
        Glide.with(this).load(userPhoto).into(profilePicture)
        profileName.text = userName
        profileEmail.text = userEmail

        // Find the notification Button
        val notifButton = view.findViewById<CardView>(R.id.notifButton)
        // Set click listener for the notification Button
        notifButton.setOnClickListener {
            // Navigate to the Notification activity
            startActivity(Intent(activity, Notification::class.java))
        }

        // Find the logout Button
        val logoutBtn = view.findViewById<CardView>(R.id.logoutBtn)
        // Set click listener for the logout Button
        logoutBtn.setOnClickListener {
            // Call the logout function
            logout()
        }

        return view
    }

    private fun logout() {
        startActivity(Intent(activity, LoginActivity::class.java))
        activity?.finish()
    }
}